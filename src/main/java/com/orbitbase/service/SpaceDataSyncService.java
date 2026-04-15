package com.orbitbase.service;

import com.orbitbase.dto.LaunchApiResponse;
import com.orbitbase.dto.LaunchApiResponse.*;
import com.orbitbase.model.*;
import com.orbitbase.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpaceDataSyncService {

    private final WebClient.Builder webClientBuilder;
    private final LaunchRepository launchRepo;
    private final MissionRepository missionRepo;
    private final AgencyRepository agencyRepo;
    private final RocketRepository rocketRepo;
    private final AstronautRepository astronautRepo;
    private final AstronautMapper astronautMapper;

    private static final String BASE_URL = "https://ll.thespacedevs.com/2.3.0";

    @Scheduled(fixedDelay = 3600000)
    public void syncLaunches() {
        log.info("Starting Launch Library 2 sync...");
        String url = BASE_URL + "/launches/?limit=25&ordering=-net";

        try {
            LaunchApiResponse response = webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(LaunchApiResponse.class)
                .block();

            if (response == null || response.getResults() == null) return;

            for (LaunchDto dto : response.getResults()) {
                if (launchRepo.existsByApiId(dto.getId())) continue;
                persistLaunch(dto);
            }

            log.info("Sync complete. Processed {} launches.", response.getResults().size());

        } catch (Exception e) {
            log.error("Sync failed: {}", e.getMessage());
        }
    }

    private void persistLaunch(LaunchDto dto) {
        Agency agency = resolveAgency(dto.getLaunchServiceProvider());
        Rocket rocket = resolveRocket(dto.getRocket());
        Mission mission = resolveMission(dto.getMission());

        Launch launch = new Launch();
        launch.setApiId(dto.getId());
        launch.setName(dto.getName());

        if (dto.getStatus() != null) {
            launch.setStatusName(dto.getStatus().getName());
            launch.setStatusAbbrev(dto.getStatus().getAbbrev());
        }
        if (dto.getNet() != null) {
            launch.setNet(Instant.parse(dto.getNet()));
        }
        if (dto.getPad() != null) {
            launch.setPadName(dto.getPad().getName());
            launch.setPadLatitude(dto.getPad().getLatitude());
            launch.setPadLongitude(dto.getPad().getLongitude());
            if (dto.getPad().getLocation() != null)
                launch.setLocationName(dto.getPad().getLocation().getName());
        }

        launch.setAgency(agency);
        launch.setRocket(rocket);
        launch.setMission(mission);

        launchRepo.save(launch);
    }

    private Agency resolveAgency(AgencyDto dto) {
        if (dto == null) return null;
        if (agencyRepo.existsByApiId(dto.getId()))
            return agencyRepo.findByApiId(dto.getId());

        Agency a = new Agency();
        a.setApiId(dto.getId());
        a.setName(dto.getName());
        a.setAbbrev(dto.getAbbrev());
        a.setType(dto.getType() != null ? dto.getType().getName() : null);
        return agencyRepo.save(a);
    }

    private Rocket resolveRocket(RocketDto dto) {
        if (dto == null || dto.getConfiguration() == null) return null;

        RocketDto.ConfigDto cfg = dto.getConfiguration();

        if (rocketRepo.existsByApiId(cfg.getId()))
            return rocketRepo.findByApiId(cfg.getId());

        Rocket r = new Rocket();
        r.setApiId(cfg.getId());
        r.setName(cfg.getName());
        r.setFullName(cfg.getFullName());
        r.setFamily(cfg.getFamily());
        r.setVariant(cfg.getVariant());
        return rocketRepo.save(r);
    }

    @Scheduled(fixedDelay = 3600000)
    public void syncAstronauts() {
        log.info("Starting astronaut sync...");
        String url = BASE_URL + "/astronauts/?limit=25";

        try {
            AstronautApiResponse response = webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(AstronautApiResponse.class)
                .block();

            if (response == null || response.getResults() == null) return;

            for (AstronautDto dto : response.getResults()) {
                if (astronautRepo.existsByApiId(dto.getId())) continue;
                astronautRepo.save(astronautMapper.toEntity(dto));
            }

            log.info("Astronaut sync complete. Processed {} astronauts.", response.getResults().size());

        } catch (Exception e) {
            log.error("Astronaut sync failed: {}", e.getMessage());
        }
    }

    private Mission resolveMission(MissionDto dto) {
        if (dto == null) return null;

        Mission m = new Mission();
        m.setName(dto.getName());
        m.setDescription(dto.getDescription());
        m.setType(dto.getType() != null ? dto.getType().getName() : null);
        if (dto.getOrbit() != null) {
            m.setOrbitName(dto.getOrbit().getName());
            m.setOrbitAbbrev(dto.getOrbit().getAbbrev());
        }
        return missionRepo.save(m);
    }
}