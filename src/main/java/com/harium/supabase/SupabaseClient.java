package com.harium.supabase;

import com.harium.supabase.database.DatabaseClient;
import okhttp3.Request;

public class SupabaseClient implements RequestDecorator {

    private final String supabaseKey;

    private final DatabaseClient databaseClient;

    public SupabaseClient(String supabaseUrl, String supabaseKey) {
        this.supabaseKey = supabaseKey;
        databaseClient = new DatabaseClient(supabaseUrl, this);
    }

    public DatabaseClient database() {
        return databaseClient;
    }

    @Override
    public Request.Builder decorate(Request.Builder builder) {
        return builder
                .addHeader("apikey", supabaseKey)
                .addHeader("Authorization", "Bearer " + supabaseKey)
                .addHeader("X-Client-Info", "supabase-java/" + Version.VERSION);
    }
}