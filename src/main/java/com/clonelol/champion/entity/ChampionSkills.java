package com.clonelol.champion.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public class ChampionSkills {

    @Id
    @Column(name = "champion_skills_id")
    private String id;    //PK

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "champion_id")
    private Champion champion;

    private String name;

    @Lob
    private String description;

    private int maxRank;

    @Lob
    private String tooltip;

    private String coolDownBurn;    //레벨당 쿨타임 변화

    private String costBurns;   //레벨당 마나 소모량 변화

    private String rangeBurn;   //레벨당 스킬 사거리 변화

    public void setChampion(Champion champion) {
        this.champion = champion;
    }
}