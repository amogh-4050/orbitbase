package com.orbitbase.service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardSummary {
    private long totalLaunches;
    private long totalMissions;
    private long totalAgencies;
    private long totalRockets;
    private long totalAstronauts;
}
