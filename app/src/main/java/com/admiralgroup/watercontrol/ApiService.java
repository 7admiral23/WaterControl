package com.admiralgroup.watercontrol;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(@Field("u") String username, @Field("p") String password);

    @GET("waterPermissions")
    Call<PermissionsResponse> getPermissions(@Header("Token") String token);

}
