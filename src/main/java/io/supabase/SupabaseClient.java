package io.supabase;

import io.supabase.database.Condition;
import io.supabase.database.Pair;
import io.supabase.database.Insert;
import io.supabase.database.JsonHelper;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

public class SupabaseClient {

    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    private final String supabaseUrl;
    private final String supabaseKey;

    private final OkHttpClient client;

    public SupabaseClient(String supabaseUrl, String supabaseKey) {
        this.supabaseUrl = extractHost(supabaseUrl);
        this.supabaseKey = supabaseKey;

        client = new OkHttpClient();
    }

    private String extractHost(String supabaseUrl) {
        final String protocolToken = "://";

        if (supabaseUrl.startsWith("http")) {
            return supabaseUrl.substring(supabaseUrl.indexOf(protocolToken) + protocolToken.length());
        }
        return supabaseUrl;
    }

    public String findAll(String table) throws IOException {
        Request request = buildRequest(table)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public String find(String table, Condition condition) throws IOException {
        Request request = buildRequest(table, condition)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public String insert(String table, String json) throws IOException {
        return upsert(table, json, false);
    }

    public String insert(String table, Pair... pairs) throws IOException {
        return insert(table, new Insert(pairs));
    }

    public String insert(String table, Insert row) throws IOException {
        String json = JsonHelper.buildJsonFromInsert(row);
        return upsert(table, json, false);
    }

    public String insert(String table, List<Insert> rows) throws IOException {
        String json = JsonHelper.buildJsonFromInsert(rows);
        return upsert(table, json, false);
    }

    public String save(String table, String json) throws IOException {
        return upsert(table, json, true);
    }

    public String save(String table, Pair... pairs) throws IOException {
        return save(table, new Insert(pairs));
    }

    public String save(String table, Insert row) throws IOException {
        String json = JsonHelper.buildJsonFromInsert(row);
        return upsert(table, json, true);
    }

    public String save(String table, List<Insert> rows) throws IOException {
        String json = JsonHelper.buildJsonFromInsert(rows);
        return upsert(table, json, true);
    }

    private String upsert(String table, String json, boolean upsert) throws IOException {
        RequestBody body = RequestBody.create(json, MEDIA_TYPE_JSON);
        Request.Builder requestBuilder = buildRequest(table);

        if (upsert) {
            requestBuilder.addHeader("Prefer", "resolution=merge-duplicates");
        }

        Request request = requestBuilder.post(body).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public String delete(String table, Condition condition) throws IOException {
        Request request = buildRequest(table, condition).delete().build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    private HttpUrl buildTableUrl(String table) {
        return new HttpUrl.Builder()
                .scheme("https")
                .host(supabaseUrl)
                .addPathSegment("rest")
                .addPathSegment("v1")
                .addPathSegment(table)
                .build();
    }

    private HttpUrl buildTableUrl(String table, Condition condition) {
        return new HttpUrl.Builder()
                .scheme("https")
                .host(supabaseUrl)
                .addPathSegment("rest")
                .addPathSegment("v1")
                .addPathSegment(table)
                .addQueryParameter(condition.getQueryParam(), condition.getQueryValue())
                .build();
    }

    private Request.Builder buildRequest(String table) {
        HttpUrl httpUrl = buildTableUrl(table);

        return new Request.Builder()
                .addHeader("apikey", supabaseKey)
                .addHeader("Authorization", "Bearer " + supabaseKey)
                .addHeader("X-Client-Info", "supabase-java/" + Version.VERSION)
                .url(httpUrl);
    }

    private Request.Builder buildRequest(String table, Condition condition) {
        HttpUrl httpUrl = buildTableUrl(table, condition);

        return new Request.Builder()
                .addHeader("apikey", supabaseKey)
                .addHeader("Authorization", "Bearer " + supabaseKey)
                .addHeader("X-Client-Info", "supabase-java/" + Version.VERSION)
                .url(httpUrl);
    }
}
