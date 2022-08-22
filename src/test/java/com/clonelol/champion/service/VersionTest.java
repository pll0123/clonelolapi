package com.clonelol.champion.service;

import com.clonelol.champion.repository.VersionRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Slf4j
@ActiveProfiles("test")
class VersionTest {

    @Autowired
    private VersionRepository versionRepository;
    @Autowired
    private ChampionsService championsService;


    @Test
    void createVersion() {
        championsService.createVersion("1.1.1");
        log.info("size < 3 ? {}",championsService.checkSize());

        championsService.createVersion("1.1.2");
        log.info("size < 3 ? {}",championsService.checkSize());

        championsService.createVersion("1.1.3");
        log.info("size < 3 ? {}",championsService.checkSize());

        championsService.createVersion("1.1.4");
        log.info("size < 3 ? {}",championsService.checkSize());

        log.info(versionRepository.min().toString());
    }


}