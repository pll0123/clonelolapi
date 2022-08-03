package com.clonelol.champion.apidto.property;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.LowerCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(LowerCaseStrategy.class)
public class ActiveSkill{

    private String id;
    private String name;
    private String description;
    private int maxRank;
    private String tooltip;
    private String coolDownBurn;    //레벨당 쿨타임 변화
    private String costBurns;   //레벨당 마나 소모량 변화
    private String rangeBurn;   //레벨당 스킬 사거리 변화
}
