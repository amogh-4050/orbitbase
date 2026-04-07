package com.orbitbase.repository;

import com.orbitbase.model.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    boolean existsByApiId(String apiId);
}