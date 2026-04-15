package com.orbitbase.controller;

import com.orbitbase.service.IAstronautService;
import com.orbitbase.service.SpaceDataSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CrewController {

    private final IAstronautService astronautService;
    private final SpaceDataSyncService syncService;

    @GetMapping("/crew")
    public String crew(Model model) {
        model.addAttribute("astronauts", astronautService.getAllAstronauts());
        return "crew";
    }

    @GetMapping("/crew/sync")
    public String sync() {
        syncService.syncAstronauts();
        return "redirect:/crew";
    }

    @GetMapping("/crew/{id}")
    public String crewDetail(@PathVariable Long id, Model model) {
        model.addAttribute("astronaut", astronautService.getAstronautById(id));
        return "crew-detail";
    }

    @GetMapping("/crew/search")
    public String search(@RequestParam String nationality, Model model) {
        model.addAttribute("astronauts", astronautService.getAstronautsByNationality(nationality));
        model.addAttribute("query", nationality);
        return "crew";
    }
}
