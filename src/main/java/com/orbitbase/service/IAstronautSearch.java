package com.orbitbase.service;

import com.orbitbase.model.Astronaut;

import java.util.List;

public interface IAstronautSearch {
    List<Astronaut> searchByNationality(String nationality);
}
