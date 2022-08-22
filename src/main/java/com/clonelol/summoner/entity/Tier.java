package com.clonelol.summoner.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Tier {
    CHALLENGER("CHALLENGER", 800),
    GRANDMASTER("GRANDMASTER", 700),
    MASTER("MASTER",600),
    DIAMOND("DIAMOND",500),
    PLATINUM("PLATINUM",400),
    GOLD("GOLD",300),
    SILVER("SILVER",200),
    BRONZE("BRONZE",100),
    IRON("IRON",0);

    private final String key;
    private final int value;
}
