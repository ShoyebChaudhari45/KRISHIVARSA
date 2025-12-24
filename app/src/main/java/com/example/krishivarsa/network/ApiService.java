package com.example.krishivarsa.network;

import com.example.krishivarsa.models.PendingUser;
import com.example.krishivarsa.network.requests.FarmerRegisterRequest;
import com.example.krishivarsa.network.requests.LoginRequest;
import com.example.krishivarsa.network.responses.FarmerRegisterResponse;
import com.example.krishivarsa.network.responses.LoginResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @POST("api/auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @Headers("Content-Type: application/json")
    @POST("api/auth/register/farmer")
    Call<FarmerRegisterResponse> registerFarmer(
            @Body FarmerRegisterRequest request
    );

    @GET("api/admin/users/pending")
    Call<List<PendingUser>> getPendingUsers(
            @Header("Authorization") String token
    );

    @PUT("api/admin/users/{userId}/approve")
    Call<Void> approveOrRejectUser(
            @Header("Authorization") String token,
            @Path("userId") String userId,
            @Body Map<String, String> body
    );

}