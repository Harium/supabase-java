package com.harium.supabase;

import com.harium.supabase.database.DatabaseAPI;
import com.harium.supabase.storage.StorageAPI;
import okhttp3.Request;

public class SupabaseClient implements RequestDecorator {

    private final String supabaseKey;

    private final DatabaseAPI databaseAPI;
    private final StorageAPI storageAPI;

    public SupabaseClient(String supabaseUrl, String supabaseKey) {
        this.supabaseKey = supabaseKey;
        databaseAPI = new DatabaseAPI(supabaseUrl, this);
        storageAPI = new StorageAPI(supabaseUrl, this);
    }

    public DatabaseAPI database() {
        return databaseAPI;
    }

    public StorageAPI storage() {
        return storageAPI;
    }

    @Override
    public Request.Builder decorate(Request.Builder builder) {
        return builder
                .addHeader("apikey", supabaseKey)
                .addHeader("Authorization", "Bearer " + supabaseKey)
                .addHeader("X-Client-Info", "supabase-java/" + Version.VERSION);
    }
}