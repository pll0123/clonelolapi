package com.clonelol.champion.repository;

import com.clonelol.champion.entity.ChampionSkills;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChampionSkillsRepository extends JpaRepository<ChampionSkills, String> {
}
