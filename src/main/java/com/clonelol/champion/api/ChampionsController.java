package com.clonelol.champion.api;

import com.clonelol.champion.entity.Champion;
import com.clonelol.champion.repository.VersionRepository;
import com.clonelol.champion.service.ChampionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.clonelol.config.ApiKeyConfiguration.*;
import static java.util.Objects.requireNonNull;

@Slf4j
@Component
@RestController
@RequiredArgsConstructor
public class ChampionsController {

    private final WebClient.Builder webClient;
    private final ChampionsService championsService;
    private final VersionRepository versionRepository;
    private final static List<Champion> freeChampion = new ArrayList<>();

    //서버 켜지면 버전이 최신인지 확인 후
//    @EventListener(ApplicationReadyEvent.class)
    public void checkVersion() {

        String[] result = checkGameVersion();
        String newVersion = result[0];

        //버전을 검색하고 새로운 버전이면 생성
        if (championsService.getVersion(newVersion).isEmpty()){
            if (championsService.checkSize()){
                championsService.deleteOldVersion();
            }
            championsService.createVersion(newVersion);
            getChampionList(newVersion);
        }
    }

    private String[] checkGameVersion() {
        return webClient
                .baseUrl(GAME_VERSION).build()
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String[].class)
                .block();
    }

    public void getChampionList(String version) {
        ChampInformationDto<SimpleInfoDto> simpleChampList = webClient
                .baseUrl(BASE_GAME_DATA)
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder.path(CHAMP_INFO)
                        .build(version)
                ).accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ChampInformationDto<SimpleInfoDto>>() {
                }).block();

        assert simpleChampList != null;
        List<Champion> entityList = simpleChampList.getNameSet()
                .stream()
                .map(name -> searchChampDetail(version, name))
                .map(DetailInfoDto::convertToEntity)
                .collect(Collectors.toList());

        championsService.initializeAll(entityList);
    }


    // 각 챔피언의 세부정보 받아오는 API 호출 메서드
    private DetailInfoDto searchChampDetail(String version, String champName) {
        return requireNonNull(
                webClient
                        .baseUrl(BASE_GAME_DATA)
                        .build()
                        .get()
                        .uri(builder -> builder.path(CHAMP_DETAILS)
                                .build(version, champName))
                        .retrieve())
                .bodyToMono(new ParameterizedTypeReference<ChampInformationDto<DetailInfoDto>>() {
                })
                .block().getValue(champName);
    }

    @GetMapping("/rotation")
    public void rotation(){
        ChampRotationDto rotationDto = webClient.baseUrl(BASE_KOR_API)
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder.path(CHAMP_ROTATIONS)
                        .queryParam("api_key", DEV_KEY).build())
                .retrieve() // 응답
                .bodyToMono(ChampRotationDto.class)
                .block();
        log.info("Rotation Info {}",rotationDto);
    }
}
