package com.clonelol.champion.service;

import com.clonelol.champion.entity.Champion;
import com.clonelol.champion.entity.ChampionStats;
import com.clonelol.champion.entity.Version;
import com.clonelol.champion.repository.ChampionRepository;
import com.clonelol.champion.repository.ChampionSkillsRepository;
import com.clonelol.champion.repository.ChampionStatsRepository;
import com.clonelol.champion.repository.VersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChampionsService {

    private final static int MAXSIZE = 3;   //최대로 저장할 버전의 갯수
    private final ChampionRepository championRepository;
    private final ChampionSkillsRepository championSkillsRepository;
    private final ChampionStatsRepository championStatsRepository;
    private final VersionRepository versionRepository;

    public Optional<Version> getVersion(String newVersion) {
        return versionRepository.findByCurVersion(newVersion);
    }

    @Transactional
    public void initializeAll(List<Champion> championList) {
        championRepository.saveAll(championList);
    }

    @Transactional
    public void initializeStatsAll(List<ChampionStats> statsList) {
        championStatsRepository.saveAll(statsList);
    }

    public Boolean checkSize(){
        return versionRepository.findAll().size() > MAXSIZE;
    }

    @Transactional
    public void createVersion(String newVersion) {
        versionRepository.save(Version.builder()
                .curVersion(newVersion)
                .build());
    }

    @Transactional
    public void deleteOldVersion() {
        Long id = versionRepository.min();
        versionRepository.deleteById(id);
    }
}
