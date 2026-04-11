package com.orbitbase.repository;

import com.orbitbase.model.Astronaut;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AstronautRepository extends JpaRepository<Astronaut, Long> {
    boolean existsByApiId(Integer apiId);
    Astronaut findByApiId(Integer apiId);
}
