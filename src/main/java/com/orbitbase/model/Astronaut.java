package com.orbitbase.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity @Table(name = "astronauts") @Data
public class Astronaut {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Integer apiId;        // LL2 integer ID
    private String name;
    private String nationality;
    @Lob
    private String bio;           // long text — bios can be several paragraphs
    private LocalDate dateOfBirth;
    private String agencyName;
    private String profileImageUrl;
}
