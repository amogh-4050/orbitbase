package com.orbitbase.controller;

import com.orbitbase.repository.AstronautRepository;
import com.orbitbase.service.SpaceDataSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CrewController {

    private final AstronautRepository astronautRepository;
    private final SpaceDataSyncService syncService;

    @GetMapping("/crew")
    public String crew(Model model) {
        model.addAttribute("astronauts", astronautRepository.findAll());
        return "crew";
    }

    @GetMapping("/crew/sync")
    public String sync() {
        syncService.syncAstronauts();
        return "redirect:/crew";
    }
}
