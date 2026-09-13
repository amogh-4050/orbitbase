package com.orbitbase.repository;

import com.orbitbase.model.Launch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LaunchRepository extends JpaRepository<Launch, Long> {
    boolean existsByApiId(String apiId);

    @Query("SELECT l FROM Launch l LEFT JOIN l.agency a WHERE " +
           "(:agency IS NULL OR LOWER(a.name) LIKE LOWER(CONCAT('%', :agency, '%'))) AND " +
           "(:status IS NULL OR LOWER(l.statusName) LIKE LOWER(CONCAT('%', :status, '%')))")
    List<Launch> searchLaunches(@Param("agency") String agency, @Param("status") String status);
}