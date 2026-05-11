package com.orbitbase.controller;

import com.orbitbase.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CrewController {

    private final IAstronautReader astronautReader;
    private final IAstronautSearch astronautSearch;
    private final SpaceDataSyncService syncService;

    @GetMapping("/crew")
    public String crew(@RequestParam(required = false) String sort, Model model) {
        if ("name".equals(sort)) {
            model.addAttribute("astronauts", astronautReader.getAllAstronautsSorted(new SortByNameStrategy()));
        } else if ("nationality".equals(sort)) {
            model.addAttribute("astronauts", astronautReader.getAllAstronautsSorted(new SortByNationalityStrategy()));
        } else {
            model.addAttribute("astronauts", astronautReader.getAllAstronauts());
        }
        model.addAttribute("sort", sort);
        return "crew";
    }

    @GetMapping("/crew/sync")
    public String sync() {
        syncService.syncAstronauts();
        return "redirect:/crew";
    }

    @GetMapping("/crew/{id}")
    public String crewDetail(@PathVariable Long id, Model model) {
        model.addAttribute("astronaut", astronautReader.getAstronautById(id));
        return "crew-detail";
    }

    @GetMapping("/crew/search")
    public String search(@RequestParam String nationality, Model model) {
        model.addAttribute("astronauts", astronautSearch.searchByNationality(nationality));
        model.addAttribute("query", nationality);
        return "crew";
    }
}
