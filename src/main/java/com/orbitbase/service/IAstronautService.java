package com.orbitbase.service;

import com.orbitbase.model.Astronaut;

import java.util.List;

public interface IAstronautService {
    List<Astronaut> getAllAstronauts();
    Astronaut getAstronautById(Long id);
    List<Astronaut> getAstronautsByNationality(String nationality);
}
