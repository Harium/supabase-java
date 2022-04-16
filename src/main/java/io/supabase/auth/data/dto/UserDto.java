package io.supabase.auth.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class UserDto {
    @JsonProperty("id")
    UUID id;
    @JsonProperty("aud")
    String aud;
    @JsonProperty("role")
    String role;
    @JsonProperty("email")
    String email;
    @JsonProperty("confirmed_at")
    Date confirmedAt;
    @JsonProperty("last_sign_in_at")
    Date lastSignInAt;
    @JsonProperty("app_metadata")
    Map<String, String> appMetadata;
    @JsonProperty("user_metadata")
    Map<String, String> userMetadata;
    @JsonProperty("created_at")
    Date createdAt;
    @JsonProperty("updated_at")
    Date updatedAt;


}
