package com.clonelol.summoner.api.summonerapi;

import com.clonelol.summoner.api.summonerapi.dto.SummonerApiDto;
import com.clonelol.summoner.entity.SummonerSimpleInfo;
import com.clonelol.summoner.repository.SummonerSimpleInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static com.clonelol.config.ApiKeyConfiguration.ENCRYPTED_SUMMONER_ID;
import static com.clonelol.config.ApiKeyConfiguration.USER_SOLO_RANK;
import static java.util.Objects.requireNonNull;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SummonerApi {

    private final RestTemplate restTemplate;
    private final SummonerSimpleInfoRepository summonerSimpleInfoRepository;
    @RequestMapping("/summoner")
    public void searchSummonerApi(/*String tier, String division*/){
        URI uri = createUriComponent(USER_SOLO_RANK)
                .encode()
                .queryParam("api_key", "RGAPI-718e4463-596c-4509-aac8-5e5851a070cb")
                .buildAndExpand("DIAMOND", "I")
                .toUri();

        RequestEntity<Void> requestEntity = RequestEntity.get(uri).build();


        List<SummonerApiDto> summonerApiDtos = requireNonNull(restTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<SummonerApiDto>>() {
        }).getBody());

        List<String> collect = summonerApiDtos
                .stream()
                .map(SummonerApiDto::getSummonerId)
                .collect(Collectors.toList());

        for (String s : collect) {
            URI summonerId = createUriComponent(ENCRYPTED_SUMMONER_ID)
                    .encode()
                    .queryParam("api_key", "RGAPI-718e4463-596c-4509-aac8-5e5851a070cb")
                    .buildAndExpand(s)
                    .toUri();
            summonerApiDtos = requireNonNull(restTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<SummonerApiDto>>() {
            }).getBody());
            System.out.println("summonerId = " + summonerApiDtos);
        }

//        List<SummonerSimpleInfo> simpleInfoList = (List<SummonerSimpleInfo>) summonerApiDtos;
//                .stream()
//                .map(this::convertToEntity)
//                .collect(Collectors.toList());
//
//        summonerSimpleInfoRepository.saveAll(simpleInfoList);
    }

    private UriComponentsBuilder createUriComponent(String uri) {
        return UriComponentsBuilder
                .fromUriString(uri);
    }

//    private SummonerSimpleInfo convertToEntity(SummonerApiDto dto){
//
//        return SummonerSimpleInfo.builder()
//                .summonerId(dto.getSummonerId())
//                .queueType(dto.getQueueType())
//                .tier(dto.getTier())
//                .grade(dto.getRank())
//                .build();
//    }
}
