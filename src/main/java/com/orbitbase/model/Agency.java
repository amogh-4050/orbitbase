package com.orbitbase.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity @Table(name = "agencies") @Data
public class Agency {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer apiId;       // LL2 integer ID
    private String name;
    private String abbrev;
    private String type;         // "Government", "Commercial"
}