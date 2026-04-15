package com.orbitbase.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data @JsonIgnoreProperties(ignoreUnknown = true)
public class LaunchApiResponse {
    private int count;
    private String next;
    private List<LaunchDto> results;

    @Data @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LaunchDto {
        private String id;
        private String name;
        private StatusDto status;
        private String net;
        private MissionDto mission;
        private RocketDto rocket;
        @JsonProperty("launch_service_provider")
        private AgencyDto launchServiceProvider;
        private PadDto pad;
    }

    @Data @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StatusDto {
        private String name;
        private String abbrev;
    }

    @Data @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MissionDto {
        private String name;
        private String description;
        private TypeDto type;
        private OrbitDto orbit;
    }

    @Data @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TypeDto {
        private String name;

        // API sometimes sends "Earth Science" (String), sometimes {"id":17,"name":"Earth Science"} (Object)
        @JsonCreator
        public static TypeDto fromValue(Object raw) {
            TypeDto dto = new TypeDto();
            if (raw instanceof String) {
                dto.setName((String) raw);
            } else if (raw instanceof Map) {
                Object n = ((Map<?, ?>) raw).get("name");
                if (n != null) dto.setName(n.toString());
            }
            return dto;
        }
    }

    @Data @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OrbitDto {
        private String name;
        private String abbrev;
    }

    @Data @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RocketDto {
        private ConfigDto configuration;

        @Data @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ConfigDto {
            private Integer id;
            private String name;
            @JsonProperty("full_name")
            private String fullName;
            private String family;
            private String variant;
        }
    }

    @Data @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AgencyDto {
        private Integer id;
        private String name;
        private String abbrev;
        private TypeDto type;
    }

    @Data @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PadDto {
        private String name;
        private String latitude;
        private String longitude;
        private LocationDto location;

        @Data @JsonIgnoreProperties(ignoreUnknown = true)
        public static class LocationDto {
            private String name;
        }
    }

    // ── Astronaut DTOs ────────────────────────────────────────────────────────

    @Data @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AstronautDto {
        private Integer id;
        private String name;
        private List<NationalityDto> nationality;
        private String bio;
        @JsonProperty("date_of_birth")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate dateOfBirth;
        private AgencyDto agency;
        @JsonProperty("profile_image")
        private String profileImage;
    }

    @Data @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NationalityDto {
        @JsonProperty("nationality_name")
        private String nationalityName;
    }

    @Data @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AstronautApiResponse {
        private int count;
        private String next;
        private List<AstronautDto> results;
    }
}
