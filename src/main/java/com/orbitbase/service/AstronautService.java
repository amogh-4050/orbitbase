package com.orbitbase.service;

import com.orbitbase.model.Astronaut;
import com.orbitbase.repository.AstronautRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AstronautService implements IAstronautReader, IAstronautSearch {

    private final AstronautRepository astronautRepository;

    // ── IAstronautReader ──────────────────────────────────────────────────────

    @Override
    public List<Astronaut> getAllAstronauts() {
        return astronautRepository.findAll();
    }

    @Override
    public List<Astronaut> getAllAstronautsSorted(SortStrategy strategy) {
        return strategy.sort(new ArrayList<>(astronautRepository.findAll()));
    }

    @Override
    public Astronaut getAstronautById(Long id) {
        return astronautRepository.findById(id).orElse(null);
    }

    // ── IAstronautSearch ──────────────────────────────────────────────────────

    @Override
    public List<Astronaut> searchByNationality(String nationality) {
        return astronautRepository.findByNationalityContainingIgnoreCase(nationality);
    }
}
