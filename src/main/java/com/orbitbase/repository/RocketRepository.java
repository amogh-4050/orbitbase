package com.orbitbase.repository;

import com.orbitbase.model.Rocket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RocketRepository extends JpaRepository<Rocket, Long> {
    boolean existsByApiId(Integer apiId);
    Rocket findByApiId(Integer apiId);
}