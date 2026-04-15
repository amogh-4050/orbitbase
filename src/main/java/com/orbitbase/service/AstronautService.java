package com.orbitbase.service;

import com.orbitbase.model.Astronaut;
import com.orbitbase.repository.AstronautRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AstronautService implements IAstronautService {

    private final AstronautRepository astronautRepository;

    @Override
    public List<Astronaut> getAllAstronauts() {
        return astronautRepository.findAll();
    }

    @Override
    public Astronaut getAstronautById(Long id) {
        return astronautRepository.findById(id).orElse(null);
    }

    @Override
    public List<Astronaut> getAstronautsByNationality(String nationality) {
        return astronautRepository.findByNationalityContainingIgnoreCase(nationality);
    }
}
