package com.clonelol.champion.apidto.property;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.LowerCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.persistence.Transient;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(LowerCaseStrategy.class)
public class PassiveSkill {

    private String id;
    private String name;
    private String description;
}
