package com.clonelol.champion.apidto.property;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.LowerCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(LowerCaseStrategy.class)
public class Stats{
    public double hp;
    public double hpPerLevel;
    public double mp;
    public double mpPerLevel;
    public double moveSpeed;
    public double armor;
    public double armorPerLevel;
    public double spellBlock;
    public double spellBlockPerLevel;
    public double attackRange;
    public double hpRegen;
    public double hpRegenPerLevel;
    public double mpRegen;
    public double mpRegenPerLevel;
    public double crit;
    public double critPerLevel;
    public double attackDamage;
    public double attackDamagePerLevel;
    public double attackSpeedPerLevel;
    public double attackSpeed;

}