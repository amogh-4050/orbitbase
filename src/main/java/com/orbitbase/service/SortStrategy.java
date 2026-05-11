package com.orbitbase.service;

import com.orbitbase.model.Astronaut;

import java.util.List;

public interface SortStrategy {
    List<Astronaut> sort(List<Astronaut> astronauts);
}
