package com.clonelol.champion.repository;

import com.clonelol.champion.entity.Version;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VersionRepository extends JpaRepository<Version,String> {

}
