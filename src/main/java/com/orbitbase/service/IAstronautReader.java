package com.orbitbase.service;

import com.orbitbase.model.Astronaut;

import java.util.List;

public interface IAstronautReader {
    List<Astronaut> getAllAstronauts();
    List<Astronaut> getAllAstronautsSorted(SortStrategy strategy);
    Astronaut getAstronautById(Long id);
}
