package com.orbitbase.repository;

import com.orbitbase.model.Launch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LaunchRepository extends JpaRepository<Launch, Long> {
    boolean existsByApiId(String apiId);
}