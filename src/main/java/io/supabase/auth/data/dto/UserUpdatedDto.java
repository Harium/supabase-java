package io.supabase.auth.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserUpdatedDto extends UserDto {
    @JsonProperty("new_email")
    String newEmail;
    @JsonProperty("email_change_sent_at")
    Date emailChangeSentAt;
}
