package com.example.krishivarsa.network;

import com.example.krishivarsa.models.VarietyModel;
import com.example.krishivarsa.network.requests.AddCropRequest;
import com.example.krishivarsa.network.requests.LoginRequest;
import com.example.krishivarsa.network.requests.RegisterRequest;
import com.example.krishivarsa.network.responses.*;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {

    // =========================
    // AUTH
    // =========================
    @POST("/api/auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("/api/auth/register")
    Call<GenericResponse> registerUser(@Body RegisterRequest request);

    @GET("/api/auth/profile")
    Call<ProfileResponse> getProfile(
            @Header("Authorization") String token
    );

    // =========================
    // ADMIN – DASHBOARD & USERS
    // =========================
    @GET("/api/admin/dashboard/stats")
    Call<AdminDashboardResponse> getAdminDashboardStats(
            @Header("Authorization") String token
    );

    @GET("/api/users/pending")
    Call<PendingUsersResponse> getPendingUsers(
            @Header("Authorization") String token
    );

    @PUT("/api/users/{userId}/approve")
    Call<GenericResponse> approveOrRejectUser(
            @Header("Authorization") String token,
            @Path("userId") String userId,
            @Body Map<String, String> body   // { status: approved/rejected }
    );

    // =========================
    // CROPS
    // =========================
    @GET("/api/crops")
    Call<GetCropsResponse> getAllCrops(
            @Header("Authorization") String token
    );


    @POST("/api/crops")
    Call<GenericResponse> addCrop(
            @Header("Authorization") String token,
            @Body AddCropRequest request
    );

    // =========================
    // VARIETIES – FARMER
    // =========================

    // Farmer → Submit variety
    @Multipart
    @POST("/api/varieties")
    Call<GenericResponse> addVariety(
            @Header("Authorization") String token,

            @Part("crop") RequestBody crop,
            @Part("name") RequestBody name,
            @Part("type") RequestBody type,
            @Part("germplasmType") RequestBody germplasmType,

            @Part("location[village]") RequestBody village,
            @Part("location[district]") RequestBody district,
            @Part("location[state]") RequestBody state,

            @Part("contactNumber") RequestBody contactNumber,

            @Part("specialCharacteristics[]") List<RequestBody> characteristics,

            @Part("notes") RequestBody notes,
            @Part("detailedDescription") RequestBody description,
            @Part("threatLevel") RequestBody threatLevel,

            @Part MultipartBody.Part image
    );

    // Farmer → My submissions
    @GET("/api/varieties/user/mine")
    Call<List<VarietyModel>> getMyVarieties(
            @Header("Authorization") String token
    );

    // Farmer → Dashboard stats
    @GET("/api/varieties/user/stats")
    Call<FarmerStatsResponse> getMyVarietyStats(
            @Header("Authorization") String token
    );

    // =========================
    // VARIETIES – ADMIN
    // =========================

    // Admin → Pending varieties
    @GET("/api/varieties/admin/pending")
    Call<PendingVarietiesResponse> getPendingVarieties(
            @Header("Authorization") String token
    );


    // Admin → Approve / Reject variety
    @PUT("/api/varieties/{id}/verify")
    Call<GenericResponse> verifyVariety(
            @Header("Authorization") String token,
            @Path("id") String varietyId,
            @Body Map<String, String> body   // { status, reason(optional) }
    );
}
