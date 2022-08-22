package com.clonelol.summoner.api.summonerapi;

import com.clonelol.summoner.api.summonerapi.dto.SummonerApiDto;
import com.clonelol.summoner.api.summonerapi.dto.SummonerIdInfoDto;
import com.clonelol.summoner.api.summonerapi.dto.property.datas.LeagueItemData.LeagueItemDto;
import com.clonelol.summoner.api.summonerapi.dto.property.datas.LeagueListDto;
import com.clonelol.summoner.service.SummonerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.clonelol.config.ApiKeyConfiguration.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SummonerApi {

    private final WebClient.Builder webClient;
    private final SummonerService summonerService;

    String[] tiers = {"GOLD", "PLATINUM", "DIAMOND"};
    String[] divisions = {"I", "II", "III", "IV"};
    String[] masterTiers = {"masterleagues", "grandmasterleagues", "challengerleagues"};

    @RequestMapping("/summoner")
    public void AutoSearch() throws InterruptedException {
        for (int page = 1; page <= 1; page++) {
            log.info("페이지 시작 : {}", page);
            searchSummonerApi(page, tiers[2], divisions[3]);
        }
    }

    @RequestMapping("/masters")
    public void AutoMasterSearch() throws InterruptedException {
        searchSummonerMasterApi(masterTiers[2]);
    }

    public void searchSummonerApi(int page, String tier, String division) throws InterruptedException {
        List<SummonerApiDto> summonerApiDtos = webClient
                .baseUrl(BASE_KOR_API)
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(USER_SOLO_RANK)
                        .queryParam("page", page)
                        .queryParam("api_key", DEV_KEY)
                        .build(tier, division))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<SummonerApiDto>>() {
                }).block();

        List<String> summonerIds = summonerApiDtos
                .stream().map(SummonerApiDto::getSummonerId)
                .collect(Collectors.toList());

        for (int i = 0; i < summonerIds.size(); i++) {
            log.info("summonersId {} : {}", i, summonerIds.get(i));
        }

        log.info("티어[{}-{}] 페이지 : {}", tier, division, page);
        log.info("탐색된 유저 수 : {}", summonerIds.size());
        List<SummonerIdInfoDto> summonerIDinfos = new ArrayList<>();
        for (var Id : summonerIds) {
            Thread.sleep(1500);
            log.info("딜레이 : {}", Id);

            summonerIDinfos.add(
                    WebClient
                    .builder()
                    .baseUrl(BASE_KOR_API)
                    .build()
                    .get()
                    .uri(uriBuilder -> uriBuilder.path(ENCRYPTED_SUMMONER_ID)
                            .queryParam("api_key",DEV_KEY)
                            .build(Id))
                    .retrieve()
                    .bodyToMono(SummonerIdInfoDto.class)
                    .block()
            );
        }

        for (int i = 0; i < summonerIDinfos.size(); i++) {
            log.info("번호: {}", i);
            log.info("SUMMONER_ID - {}", summonerIDinfos.get(i).getSummonerId());
            log.info("PUUID - {}", summonerIDinfos.get(i).getPuuId());
            log.info("------------------------------");
        }
        summonerService.initializeAllIdInfo(summonerIDinfos);

    }

    public void searchSummonerMasterApi(String MasterTiers) throws InterruptedException {
        LeagueListDto leagueListDto = webClient
                .baseUrl(BASE_KOR_API)
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(-1))
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/lol/league/v4/{MasterTiers}/by-queue/RANKED_SOLO_5x5")
                        .queryParam("api_key", DEV_KEY)
                        .build(MasterTiers))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(LeagueListDto.class)
                .block();

        System.out.println("여기까지 테스트");

        List<String> summonerIds = leagueListDto.getEntries()
                .stream()
                .map(LeagueItemDto::getSummonerId)
                .collect(Collectors.toList());

        for (int i = 0; i < summonerIds.size(); i++) {
            log.info("summonersId {} : {}", i, summonerIds.get(i));
        }

        log.info("탐색된 유저 수 : {}", summonerIds.size());
        List<SummonerIdInfoDto> summonerIDinfos = new ArrayList<>();
        for (var Id : summonerIds) {
            Thread.sleep(1500);
            log.info("딜레이 : {}", Id);

            summonerIDinfos.add(
                    WebClient
                            .builder()
                            .baseUrl(BASE_KOR_API)
                            .build()
                            .get()
                            .uri(uriBuilder -> uriBuilder.path(ENCRYPTED_SUMMONER_ID)
                                    .queryParam("api_key",DEV_KEY)
                                    .build(Id))
                            .retrieve()
                            .bodyToMono(SummonerIdInfoDto.class)
                            .block()
            );
        }

        for (int i = 0; i < summonerIDinfos.size(); i++) {
            log.info("번호: {}", i);
            log.info("SUMMONER_ID - {}", summonerIDinfos.get(i).getSummonerId());
            log.info("PUUID - {}", summonerIDinfos.get(i).getPuuId());
            log.info("------------------------------");
        }
        summonerService.initializeAllIdInfo(summonerIDinfos);

    }
}
