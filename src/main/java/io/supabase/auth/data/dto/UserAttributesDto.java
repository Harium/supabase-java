package io.supabase.auth.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAttributesDto {
    @JsonProperty("email")
    String email;
    @JsonProperty("password")
    String password;
    @JsonProperty("email_change_token")
    String emailChangeToken;
    @JsonProperty("data")
    Object data;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailChangeToken() {
        return emailChangeToken;
    }

    public void setEmailChangeToken(String emailChangeToken) {
        this.emailChangeToken = emailChangeToken;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
