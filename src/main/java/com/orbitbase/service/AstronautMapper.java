package com.orbitbase.service;

import com.orbitbase.dto.LaunchApiResponse.AstronautDto;
import com.orbitbase.model.Astronaut;
import org.springframework.stereotype.Component;

/**
 * SRP: single responsibility is mapping AstronautDto (LL2 API) → Astronaut (domain entity).
 * SpaceDataSyncService delegates all mapping here.
 */
@Component
public class AstronautMapper {

    public Astronaut toEntity(AstronautDto dto) {
        String nationality = (dto.getNationality() != null && !dto.getNationality().isEmpty())
                ? dto.getNationality().get(0).getNationalityName()
                : null;

        return new AstronautBuilder()
                .withApiId(dto.getId())
                .withName(dto.getName())
                .withNationality(nationality)
                .withBio(dto.getBio())
                .withDateOfBirth(dto.getDateOfBirth())
                .withAgencyName(dto.getAgency() != null ? dto.getAgency().getName() : null)
                .withProfileImageUrl(dto.getProfileImage())
                .build();
    }
}
