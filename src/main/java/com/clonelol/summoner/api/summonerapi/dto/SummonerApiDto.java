package com.clonelol.summoner.api.summonerapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.LowerCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
public class SummonerApiDto {

    private String summonerId;
    @JsonProperty("puuid")
    private String pUuid;
}
