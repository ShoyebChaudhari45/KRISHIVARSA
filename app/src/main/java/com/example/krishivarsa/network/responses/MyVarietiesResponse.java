package com.example.krishivarsa.network.responses;

import java.util.List;

public class MyVarietiesResponse {

    public boolean success;
    public int count;
    public List<Variety> varieties;

    public static class Variety {
        public String id;
        public String name;
        public String type;
        public String germplasmType;
        public String threatLevel;
        public boolean isVerified;
        public String verificationStatus;
        public String createdAt;
    }
}
