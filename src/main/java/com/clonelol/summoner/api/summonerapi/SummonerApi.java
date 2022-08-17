package com.clonelol.summoner.api.summonerapi;

import com.clonelol.summoner.api.summonerapi.dto.SummonerApiDto;
import com.clonelol.summoner.api.summonerapi.dto.SummonerIdInfoDto;
import com.clonelol.summoner.entity.SummonerSimpleInfo;
import com.clonelol.summoner.service.MatchService;
import com.clonelol.summoner.service.SummonerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.clonelol.config.ApiKeyConfiguration.*;
import static java.util.Objects.requireNonNull;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SummonerApi {

    private final RestTemplate restTemplate;
    private final MatchService matchService;
    private final SummonerService summonerService;

    String[] tiers = {"GOLD", "PLATINUM", "DIAMOND"};
    String[] divisions = {"I", "II", "III", "IV"};

    @RequestMapping("/summoner")
    public void AutoSearch() throws InterruptedException {
        for (int page = 1; page <= 5; page++) {
            log.info("페이지 시작 : {}", page);
            searchSummonerApi(page, tiers[2], divisions[0]);
        }
    }

    public void searchSummonerApi(int page, String tier, String division) throws InterruptedException {
        URI uri = createUriComponent(USER_SOLO_RANK)
                .encode()
                .queryParam("page", page)
                .queryParam("api_key", DEV_KEY)
                .buildAndExpand(tier, division)
                .toUri();

        RequestEntity<Void> requestEntity = RequestEntity.get(uri).build();

        List<SummonerApiDto> summonerApiDtos = requireNonNull(restTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<SummonerApiDto>>() {
        }).getBody());

        List<String> summonerIds = summonerApiDtos
                .stream().map(SummonerApiDto::getSummonerId)
                .collect(Collectors.toList());

        for (int i = 0; i < summonerIds.size(); i++) log.info("summonersId {} : {}", i, summonerIds.get(i));

        log.info("티어[{}-{}] 페이지 : {}", tier, division, page);
        log.info("탐색된 유저 수 : {}", summonerIds.size());
        List<SummonerIdInfoDto> summonerIDinfos = new ArrayList<>();
        for (var Id : summonerIds) {
            Thread.sleep(1500);
            log.info("딜레이 : {}", Id);

            summonerIDinfos.add(
                    WebClient
                    .builder()
                    .baseUrl("https://kr.api.riotgames.com/")
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

    @RequestMapping("/match")
    public void searchMatchApi() {
        URI uri = createUriComponent(MATCH_ID)
                .encode()
                .queryParam("queue", 420)
                .queryParam("type", "ranked")
                .queryParam("start", 0)
                .queryParam("count", 100)
                .queryParam("api_key", DEV_KEY)
                .buildAndExpand(/*puuid*/)
                .toUri();

        List<String> result = restTemplate.getForObject(uri, ArrayList.class);
        matchService.initializeAll(result);
    }


    private UriComponentsBuilder createUriComponent(String uri) {
        return UriComponentsBuilder
                .fromUriString(uri);
    }

    private SummonerSimpleInfo convertToEntity(SummonerApiDto dto) {

        return SummonerSimpleInfo.builder()
                .summonerId(dto.getSummonerId())

                .build();
    }
}
