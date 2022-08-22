package com.clonelol.champion.entity;

import com.clonelol.summoner.entity.MatchSummary;
import com.clonelol.web.dto.RotationResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Champion {

    @Id
    @GeneratedValue
    @Column(name = "champion_id")
    private Long id;

    private Long key;
    private String nameEn;  //챔피언 영문명
    private String nameKr;  //챔피언 명칭(Regions에 따라 언어 다름 현재: ko_KR)
    private String title;   //챔피언 타이틀
    private String portrait;    //챔피언 초상화

    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "champion_stats_id")
    private ChampionStats champStats;

    @OneToMany(mappedBy = "champion", cascade = ALL)
    private final List<ChampionSkills> skills = new ArrayList<>();

    @OneToMany(mappedBy = "champion", cascade = ALL)
    private final List<ChampionSkins> skins = new ArrayList<>();

    @OneToMany(cascade = ALL)
    @JoinColumn(name = "match_summary_id")
    private final List<MatchSummary> matchSummaries = new ArrayList<>();

    @Builder
    public Champion(Long key, String nameEn, String nameKr, String title, String portrait, ChampionStats championStats, List<ChampionSkills> skills, List<ChampionSkins> skins) {
        this.key = key;
        this.nameEn = nameEn;
        this.nameKr = nameKr;
        this.title = title;
        this.portrait = portrait;
        this.champStats = championStats;
        setSkills(skills);
        setSkins(skins);
    }

    private void setSkills(List<ChampionSkills> championSkills) {
        this.skills.addAll(championSkills);
        this.skills.forEach(skill -> skill.setChampion(this));
    }

    private void setSkins(List<ChampionSkins> championSkins) {
        this.skins.addAll(championSkins);
        this.skins.forEach(skin -> skin.setChampion(this));
    }

    //로테이션 응답 Dto로 변환
    public RotationResponse toRotationResDto() {
        RotationResponse dto = new RotationResponse();
        dto.setName(this.nameKr);
        dto.setPortrait(this.portrait);
        return dto;
    }

    public void addMatchSummary(MatchSummary matchSummary) {
        this.matchSummaries.add(matchSummary);
    }
}