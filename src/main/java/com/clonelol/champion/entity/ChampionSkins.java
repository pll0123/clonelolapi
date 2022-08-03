package com.clonelol.champion.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Entity
public class ChampionSkins {

    @Id
    @Column(name = "champion_skins_id")
    private String id;    //PK

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "champion_id")
    private Champion champion;

    private String name;

    private int num;

    private Boolean chromas;

    public void setChampion(Champion champion) {
        this.champion = champion;
    }
}