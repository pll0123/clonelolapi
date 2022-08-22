package com.clonelol.summoner.api.summonerapi.dto.property.datas.LeagueItemData;

import com.clonelol.summoner.entity.Rank;
import com.clonelol.summoner.entity.Tier;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeagueItemDto {

    private String summonerId;
    private String summonerName;
    private String rank;
    private int leaguePoints;
    private int wins;
    private int losses;
    private boolean hotStreak;
    private boolean veteran;
    private boolean freshBlood;
    private boolean inactive;

}
