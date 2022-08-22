package com.clonelol.champion.api;

import com.clonelol.champion.api.property.*;
import com.clonelol.champion.entity.ChampionSkills;
import com.clonelol.config.ApiKeyConfiguration;
import com.clonelol.champion.entity.Champion;
import com.clonelol.champion.entity.ChampionStats;
import com.clonelol.champion.entity.ChampionSkins;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DetailInfoDto {

    public String id;   //챔피언 영문명(고정)
    public Long key;    //키
    public String name; //챔피언 명(Region에 따라 다름 :현재 한글)
    public String title;//챔피언 타이틀
    private Info info;
    private List<Skin> skins = new ArrayList<>();
    private Stats stats;
    private List<ActiveSkill> spells = new ArrayList<>();
    private PassiveSkill passive;

    public Champion convertToEntity(){
        return Champion.builder()
                .key(key)
                .title(title)
                .nameKr(name)
                .nameEn(id)
                .portrait(ApiKeyConfiguration.CHAMP_IMG_PORTRAIT+id+".png")
                .championStats(convertToStats())
                .skills(setSkill(passive, spells))
                .skins(setSkin(skins))
                .build();
    }

    public ChampionStats convertToStats() {
        return ChampionStats.builder()
                .hp(stats.getHp())
                .hpperlevel(stats.getHpPerLevel())
                .mp(stats.getMp())
                .mpperlevel(stats.getMpPerLevel())
                .movespeed(stats.getMoveSpeed())
                .armor(stats.getArmor())
                .armorperlevel(stats.getArmorPerLevel())
                .spellblock(stats.getSpellBlock())
                .spellblockperlevel(stats.getSpellBlockPerLevel())
                .attackrange(stats.getAttackRange())
                .hpregen(stats.getHpRegen())
                .hpregenperlevel(stats.getHpRegenPerLevel())
                .mpregen(stats.getMpRegen())
                .mpregenperlevel(stats.getMpRegenPerLevel())
                .crit(stats.getCrit())
                .critperlevel(stats.getCritPerLevel())
                .attackdamage(stats.getAttackDamage())
                .attackdamageperlevel(stats.getAttackDamagePerLevel())
                .attackspeed(stats.getAttackSpeed())
                .attackspeedperlevel(stats.getAttackSpeedPerLevel())
                .build();
    }

    private List<ChampionSkills> setSkill(PassiveSkill passive, List<ActiveSkill> skills){

        List<ChampionSkills> championSkills = skills.stream()
                .map(skill -> ChampionSkills.builder()
                            .id(skill.getId())
                            .name(skill.getName())
                            .description(skill.getDescription())
                            .maxRank(skill.getMaxRank())
                            .tooltip(skill.getTooltip())
                            .coolDownBurn(skill.getCoolDownBurn())
                            .costBurns(skill.getCostBurns())
                            .rangeBurn(skill.getRangeBurn())
                            .build())
                .collect(Collectors.toList());

        ChampionSkills passiveEntity = ChampionSkills.builder()
                .id("P-"+id)
                .name(passive.getName())
                .description(passive.getDescription())
                .build();

        championSkills.add(passiveEntity);

        return championSkills;
    }
    
    private List<ChampionSkins> setSkin(List<Skin> skins){

        List<ChampionSkins> championSkins = skins.stream()
                .map(skin -> ChampionSkins.builder()
                            .id(skin.getId())
                            .name(skin.getName())
                            .num(skin.getNum())
                            .chromas(skin.getChromas())
                            .build())
                .collect(Collectors.toList());

        return championSkins;
    }

}
