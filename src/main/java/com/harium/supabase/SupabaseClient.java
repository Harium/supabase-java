package com.harium.supabase;

import com.harium.postgrest.Condition;
import com.harium.postgrest.PostgrestClient;
import com.sun.corba.se.impl.util.Version;
import okhttp3.HttpUrl;
import okhttp3.Request;

public class SupabaseClient extends PostgrestClient {

    private final String supabaseKey;

    public SupabaseClient(String supabaseUrl, String supabaseKey) {
        super(supabaseUrl);
        this.supabaseKey = supabaseKey;
    }

    protected HttpUrl buildTableUrl(String table) {
        return new HttpUrl.Builder()
                .scheme("https")
                .host(baseUrl)
                .addPathSegment("rest")
                .addPathSegment("v1")
                .addPathSegment(table)
                .build();
    }

    protected HttpUrl buildTableUrl(String table, Condition condition) {
        return new HttpUrl.Builder()
                .scheme("https")
                .host(baseUrl)
                .addPathSegment("rest")
                .addPathSegment("v1")
                .addPathSegment(table)
                .addQueryParameter(condition.getQueryParam(), condition.getQueryValue())
                .build();
    }

    protected Request.Builder buildRequest(String table) {
        HttpUrl httpUrl = buildTableUrl(table);

        return new Request.Builder()
                .addHeader("apikey", supabaseKey)
                .addHeader("Authorization", "Bearer " + supabaseKey)
                .addHeader("X-Client-Info", "supabase-java/" + Version.VERSION)
                .url(httpUrl);
    }

    protected Request.Builder buildRequest(String table, Condition condition) {
        HttpUrl httpUrl = buildTableUrl(table, condition);

        return new Request.Builder()
                .addHeader("apikey", supabaseKey)
                .addHeader("Authorization", "Bearer " + supabaseKey)
                .addHeader("X-Client-Info", "supabase-java/" + Version.VERSION)
                .url(httpUrl);
    }
}