package com.orbitbase.controller;

import com.orbitbase.repository.AgencyRepository;
import com.orbitbase.repository.LaunchRepository;
import com.orbitbase.repository.MissionRepository;
import com.orbitbase.repository.RocketRepository;
import com.orbitbase.service.OrbitDataFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Tag(name = "Launch Data", description = "Pages for launches, missions, agencies, rockets, and dashboard")
public class DataController {

    private final LaunchRepository launchRepository;
    private final AgencyRepository agencyRepository;
    private final MissionRepository missionRepository;
    private final RocketRepository rocketRepository;
    private final OrbitDataFacade orbitDataFacade;

    @Operation(summary = "Launches page", description = "Lists all synced launches from Launch Library 2")
    @GetMapping("/launches")
    public String launches(Model model) {
        model.addAttribute("launches", launchRepository.findAll());
        return "launches";
    }

    @Operation(summary = "Agencies page", description = "Lists all space agencies")
    @GetMapping("/agencies")
    public String agencies(Model model) {
        model.addAttribute("agencies", agencyRepository.findAll());
        return "agencies";
    }

    @Operation(summary = "Missions page", description = "Lists all missions with type and orbit")
    @GetMapping("/missions")
    public String missions(Model model) {
        model.addAttribute("missions", missionRepository.findAll());
        return "missions";
    }

    @Operation(summary = "Rockets page", description = "Lists all rockets with family and variant")
    @GetMapping("/rockets")
    public String rockets(Model model) {
        model.addAttribute("rockets", rocketRepository.findAll());
        return "rockets";
    }

    @Operation(summary = "Dashboard", description = "Shows counts for all 5 tables via OrbitDataFacade")
    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("summary", orbitDataFacade.getDashboardSummary());
        return "dashboard";
    }
}
