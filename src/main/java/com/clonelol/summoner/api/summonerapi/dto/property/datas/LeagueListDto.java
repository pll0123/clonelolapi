package com.clonelol.summoner.api.summonerapi.dto.property.datas;

import com.clonelol.summoner.api.summonerapi.dto.property.datas.LeagueItemData.LeagueItemDto;
import com.clonelol.summoner.entity.Rank;
import com.clonelol.summoner.entity.Tier;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LeagueListDto {

    private String leagueId;
    private String queue;
    private Tier tier;
    private List<LeagueItemDto> entries;
    private String name;

}
