package com.orbitbase.controller;

import com.orbitbase.repository.AgencyRepository;
import com.orbitbase.repository.LaunchRepository;
import com.orbitbase.repository.MissionRepository;
import com.orbitbase.repository.RocketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DataController {

    private final LaunchRepository launchRepository;
    private final AgencyRepository agencyRepository;
    private final MissionRepository missionRepository;
    private final RocketRepository rocketRepository;

    @GetMapping("/launches")
    public String launches(Model model) {
        model.addAttribute("launches", launchRepository.findAll());
        return "launches";
    }

    @GetMapping("/agencies")
    public String agencies(Model model) {
        model.addAttribute("agencies", agencyRepository.findAll());
        return "agencies";
    }

    @GetMapping("/missions")
    public String missions(Model model) {
        model.addAttribute("missions", missionRepository.findAll());
        return "missions";
    }

    @GetMapping("/rockets")
    public String rockets(Model model) {
        model.addAttribute("rockets", rocketRepository.findAll());
        return "rockets";
    }
}
