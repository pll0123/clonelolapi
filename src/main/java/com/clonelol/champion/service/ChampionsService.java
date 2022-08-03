package com.clonelol.champion.service;

import com.clonelol.champion.entity.Champion;
import com.clonelol.champion.entity.ChampionStats;
import com.clonelol.champion.repository.ChampionRepository;
import com.clonelol.champion.repository.ChampionSkillsRepository;
import com.clonelol.champion.repository.ChampionStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChampionsService {

    private final ChampionRepository championRepository;
    private final ChampionSkillsRepository championSkillsRepository;
    private final ChampionStatsRepository championStatsRepository;

    @Transactional
    public void initializeAll(List<Champion> championList) {
        championRepository.saveAll(championList);
    }

    @Transactional
    public void initializeStatsAll(List<ChampionStats> statsList) {
        championStatsRepository.saveAll(statsList);
    }

}
