package com.example.krishivarsa.network.responses;

public class AddCropResponse {
    public boolean success;
    public Crop crop;

    public static class Crop {
        public String id;
        public String name;
        public String category;
    }
}
