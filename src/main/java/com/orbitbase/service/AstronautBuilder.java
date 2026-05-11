package com.orbitbase.service;

import com.orbitbase.model.Astronaut;
import java.time.LocalDate;

/**
 * GoF Builder pattern — Creational.
 *
 * Constructs an Astronaut step-by-step from LL2 API data.
 * Only apiId and name are required; all other fields are optional.
 * Calling build() before setting required fields throws IllegalStateException.
 *
 * Usage:
 *   Astronaut a = new AstronautBuilder()
 *       .withApiId(42)
 *       .withName("Neil Armstrong")
 *       .withNationality("American")
 *       .build();
 */
public class AstronautBuilder {

    private Integer apiId;
    private String name;
    private String nationality;
    private String bio;
    private LocalDate dateOfBirth;
    private String agencyName;
    private String profileImageUrl;

    public AstronautBuilder withApiId(Integer apiId) {
        this.apiId = apiId;
        return this;
    }

    public AstronautBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public AstronautBuilder withNationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public AstronautBuilder withBio(String bio) {
        this.bio = bio;
        return this;
    }

    public AstronautBuilder withDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public AstronautBuilder withAgencyName(String agencyName) {
        this.agencyName = agencyName;
        return this;
    }

    public AstronautBuilder withProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
        return this;
    }

    public Astronaut build() {
        if (apiId == null) throw new IllegalStateException("apiId is required");
        if (name == null || name.isBlank()) throw new IllegalStateException("name is required");

        Astronaut astronaut = new Astronaut();
        astronaut.setApiId(apiId);
        astronaut.setName(name);
        astronaut.setNationality(nationality);
        astronaut.setBio(bio);
        astronaut.setDateOfBirth(dateOfBirth);
        astronaut.setAgencyName(agencyName);
        astronaut.setProfileImageUrl(profileImageUrl);
        return astronaut;
    }
}
