package com.clonelol.champion.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = PROTECTED)
public class Version {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String curVersion;

    @Builder
    public Version(String curVersion) {
        this.curVersion = curVersion;
    }
}
