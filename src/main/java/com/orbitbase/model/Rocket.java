package com.orbitbase.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity @Table(name = "rockets") @Data
public class Rocket {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer apiId;
    private String name;
    private String fullName;
    private String family;
    private String variant;
}