package io.supabase.auth.data.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Map;

public class ParsedToken {
    @JsonProperty("exp")
    Date exp;
    @JsonProperty("sub")
    String sub;
    @JsonProperty("email")
    String email;
    @JsonProperty("app_metadata")
    Map<String, String> appMetadata;
    @JsonProperty("user_metadata")
    Map<String, String> userMetadata;
    @JsonProperty("role")
    String role;

    public Date getExp() {
        return exp;
    }

    public void setExp(Date exp) {
        this.exp = exp;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, String> getAppMetadata() {
        return appMetadata;
    }

    public void setAppMetadata(Map<String, String> appMetadata) {
        this.appMetadata = appMetadata;
    }

    public Map<String, String> getUserMetadata() {
        return userMetadata;
    }

    public void setUserMetadata(Map<String, String> userMetadata) {
        this.userMetadata = userMetadata;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
