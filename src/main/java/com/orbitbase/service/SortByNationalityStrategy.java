package com.orbitbase.service;

import com.orbitbase.model.Astronaut;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortByNationalityStrategy implements SortStrategy {

    @Override
    public List<Astronaut> sort(List<Astronaut> astronauts) {
        List<Astronaut> sorted = new ArrayList<>(astronauts);
        sorted.sort(Comparator.comparing(a -> a.getNationality() != null ? a.getNationality() : ""));
        return sorted;
    }
}
