package com.clonelol.champion.controller;

import com.clonelol.champion.apidto.ChampInformationDto;
import com.clonelol.champion.apidto.DetailInfoDto;
import com.clonelol.champion.apidto.SimpleInfoDto;
import com.clonelol.champion.entity.Champion;
import com.clonelol.champion.entity.Version;
import com.clonelol.champion.repository.VersionRepository;
import com.clonelol.champion.service.ChampionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.clonelol.config.ApiKeyConfiguration.*;

@Component
@RequiredArgsConstructor
public class ChampionsController {

    private final RestTemplate restTemplate;
    private final ChampionsService championsService;
    private final VersionRepository versionRepository;
    private final static List<Champion> freeChampion = new ArrayList<>();

    //서버 켜지면 버전이 최신인지 확인 후
    @EventListener(ApplicationReadyEvent.class)
    public void checkVersion() {

        String[] result = checkChampVersion();
        String newVersion = result[0];

        //저장된 데이터가
        //있으면 그대로 가져오고, 없으면 최신 버전을 생성하면서 챔프 정보를 불러온다.
        Version version = versionRepository.findById("Version")
                .orElseGet(()-> create(newVersion));

        //저장된 데이터가 있을 때,
        //최신 버전인지 확인하고 아니라면 업데이트 한다.
        if(!version.isLatestVersion(newVersion)){
            updateChamp4newVersion(newVersion, version);
        }

    }

    //버전데이터가 없을경우 최초 등록 메서드
    private Version create(String newVersion) {
        Version version = versionRepository.save(Version.builder()
                .id("Version")
                .latestVersion(newVersion)
                .build());
        getChampionList(newVersion);

        return version;
    }

    //버전이 바뀔경우 기존 챔피언 정보 업데이트
    private void updateChamp4newVersion(String newVersion, Version findVersion) {
        findVersion.updateLatestVersion(newVersion);
        versionRepository.save(findVersion);
        getChampionList(newVersion);
    }

    private String[] checkChampVersion() {
        URI uri = createUriComponent(GAME_VERSION)
                .encode()
                .build().toUri();

        String[] result = restTemplate.getForObject(uri, String[].class);
        return result;
    }

    private void getChampionList(String version) {
        URI uri = createUriComponent(CHAMP_INFO)
                .encode()
                .buildAndExpand(version)
                .toUri();

        RequestEntity<Void> build = RequestEntity.get(uri)
                .build();

        ChampInformationDto<SimpleInfoDto> champList = restTemplate
                .exchange(build, new ParameterizedTypeReference<ChampInformationDto<SimpleInfoDto>>() {})
                .getBody();

        List<Champion> entityList = champList.getNameSet()
                .stream()
                .map(name -> searchChampDetail(version, name))
                .map(DetailInfoDto::convertToEntity)
                .collect(Collectors.toList());

        championsService.initializeAll(entityList);
    }


    // 각 챔피언의 세부정보 받아오는 API 호출 메서드
    private DetailInfoDto searchChampDetail(String version, String champName) {
        //String -> URI type 변경.
        URI url = createUriComponent(CHAMP_DETAILS)
                .encode()
                .buildAndExpand(version, champName)
                .toUri();

        RequestEntity<Void> request = RequestEntity.get(url)
                .build();

        ChampInformationDto<DetailInfoDto> infoList = restTemplate.exchange(request, new ParameterizedTypeReference<ChampInformationDto<DetailInfoDto>>() {
                })
                .getBody();

        return infoList.getValue(champName);
    }

    private UriComponentsBuilder createUriComponent(String uri) {
        return UriComponentsBuilder
                .fromUriString(uri);
    }

}
