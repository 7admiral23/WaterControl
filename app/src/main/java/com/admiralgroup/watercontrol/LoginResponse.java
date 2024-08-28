package com.admiralgroup.watercontrol;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("Token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
