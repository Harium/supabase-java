package io.supabase.exceptions;

public class UrlNotFoundException extends Exception {
    public UrlNotFoundException() {
        super("The GoTrue url is not defined");
    }
}
