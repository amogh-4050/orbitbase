package com.orbitbase.repository;

import com.orbitbase.model.Astronaut;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AstronautRepository extends JpaRepository<Astronaut, Long> {
    boolean existsByApiId(Integer apiId);
    Astronaut findByApiId(Integer apiId);
    List<Astronaut> findByNationalityContainingIgnoreCase(String nationality);
}
