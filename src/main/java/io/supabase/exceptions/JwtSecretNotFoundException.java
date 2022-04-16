package io.supabase.exceptions;

public class JwtSecretNotFoundException extends Exception {
    public JwtSecretNotFoundException() {
        super("JWT Secret is not defined.");
    }
}
