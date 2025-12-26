package com.example.krishivarsa.network.responses;

import java.util.List;

public class GetCropsResponse {

    public boolean success;
    public List<Crop> crops;

    public static class Crop {
        public String _id;
        public String name;
    }
}
