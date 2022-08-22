package com.clonelol.champion.repository;

import com.clonelol.champion.entity.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VersionRepository extends JpaRepository<Version, Long> {
    Optional<Version> findByCurVersion(String version);

    @Query(value = "select min(v.id) from Version v")
    Long min();
}
