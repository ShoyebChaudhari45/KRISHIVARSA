package com.example.krishivarsa.network.responses;

public class AdminDashboardResponse {
    public boolean success;
    public Stats stats;

    public static class Stats {
        public int totalFarmers;
        public int totalInstitutions;
        public int totalCrops;
        public int totalVarieties;
    }
}
