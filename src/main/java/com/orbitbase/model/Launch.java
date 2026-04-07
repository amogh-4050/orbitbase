package com.orbitbase.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;

@Entity @Table(name = "launches") @Data
public class Launch {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String apiId;        // LL2 UUID
    private String name;
    private String statusName;   // "Launch Successful", "Go", etc.
    private String statusAbbrev;
    private Instant net;         // actual launch datetime

    private String padName;
    private String padLatitude;
    private String padLongitude;
    private String locationName;

    @ManyToOne
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @ManyToOne
    @JoinColumn(name = "agency_id")
    private Agency agency;

    @ManyToOne
    @JoinColumn(name = "rocket_id")
    private Rocket rocket;
}