package com.orbitbase.service;

import com.orbitbase.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Facade Pattern: provides a single simplified interface over five repositories.
 * Controllers call getDashboardSummary() instead of injecting and querying
 * each repository individually.
 */
@Service
@RequiredArgsConstructor
public class OrbitDataFacade {

    private final LaunchRepository launchRepository;
    private final MissionRepository missionRepository;
    private final AgencyRepository agencyRepository;
    private final RocketRepository rocketRepository;
    private final AstronautRepository astronautRepository;

    public DashboardSummary getDashboardSummary() {
        return new DashboardSummary(
                launchRepository.count(),
                missionRepository.count(),
                agencyRepository.count(),
                rocketRepository.count(),
                astronautRepository.count()
        );
    }
}
