package io.supabase.auth.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Map;

@Getter
public class SettingsDto {
    @JsonProperty("external")
    Map<String, Boolean> external;
    @JsonProperty("external_labels")
    Object externalLabels;
    @JsonProperty("disable_signup")
    Boolean disableSignup;
    @JsonProperty("autoconfirm")
    Boolean autoconfirm;

    public Map<String, Boolean> getExternal() {
        return external;
    }

    public void setExternal(Map<String, Boolean> external) {
        this.external = external;
    }

    public Object getExternalLabels() {
        return externalLabels;
    }

    public void setExternalLabels(Object externalLabels) {
        this.externalLabels = externalLabels;
    }

    public Boolean getDisableSignup() {
        return disableSignup;
    }

    public void setDisableSignup(Boolean disableSignup) {
        this.disableSignup = disableSignup;
    }

    public Boolean getAutoconfirm() {
        return autoconfirm;
    }

    public void setAutoconfirm(Boolean autoconfirm) {
        this.autoconfirm = autoconfirm;
    }
}
