package io.supabase.auth.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationDto {
    @JsonProperty("access_token")
    String accessToken;
    @JsonProperty("token_type")
    String tokenType;
    @JsonProperty("expires_in")
    int expiresIn;
    @JsonProperty("refresh_token")
    String refreshToken;
    @JsonProperty("user")
    UserDto user;

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public UserDto getUser() {
        return user;
    }
}
