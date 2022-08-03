package com.clonelol.champion.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = PROTECTED)
public class Version {
    @Id
    private String id;

    private String latestVersion;

    public boolean isLatestVersion(String latestVersion) {
        return this.latestVersion.equals(latestVersion);
    }

    public void updateLatestVersion(String newVersion) {
        this.latestVersion = newVersion;
    }

    @Builder
    public Version(String id, String latestVersion) {
        this.id = id;
        this.latestVersion = latestVersion;
    }
}
