package com.orbitbase.repository;

import com.orbitbase.model.Agency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgencyRepository extends JpaRepository<Agency, Long> {
    boolean existsByApiId(Integer apiId);
    Agency findByApiId(Integer apiId);
}