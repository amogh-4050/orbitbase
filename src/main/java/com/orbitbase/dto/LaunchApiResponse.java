package com.orbitbase.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

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
        private TypeDto type;      // ← was String, now object
        private OrbitDto orbit;
    }

    @Data @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TypeDto {
        private String name;
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
        private TypeDto type;      // ← was String, now object
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
}