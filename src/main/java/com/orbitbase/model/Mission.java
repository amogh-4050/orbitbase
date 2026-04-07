package com.orbitbase.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity @Table(name = "missions") @Data
public class Mission {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String apiId;        // LL2 UUID
    private String name;
    private String description;
    private String type;         // e.g. "Earth Science", "Communications"
    private String orbitName;    // e.g. "LEO", "GTO"
    private String orbitAbbrev;
}