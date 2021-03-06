package com.clonelol.champion.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleInfoDto {

    public String id;
    public String key;
    public String name;

}
