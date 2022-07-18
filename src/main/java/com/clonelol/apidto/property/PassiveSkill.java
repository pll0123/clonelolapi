package com.clonelol.apidto.property;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PassiveSkill {

    private String name;
    private String description;
}