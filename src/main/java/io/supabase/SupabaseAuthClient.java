package io.supabase;

import com.harium.postgrest.Condition;
import com.harium.postgrest.Insert;
import com.harium.postgrest.Pair;
import com.harium.postgrest.json.JsonHelper;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

public class SupabaseAuthClient {

    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    protected final String supabaseUrl;

    private final String supabaseKey;

    protected final OkHttpClient client;

    protected boolean httpsEnabled = true;

    public SupabaseAuthClient(String baseUrl, String supabaseKey) {
        this.supabaseUrl = extractHost(baseUrl);
        this.supabaseKey = supabaseKey;

        client = new OkHttpClient();
    }

    public SupabaseAuthClient withHttps(boolean https) {
        this.httpsEnabled = https;
        return this;
    }

    private String extractHost(String baseUrl) {
        final String protocolToken = "://";

        if (baseUrl.startsWith("http")) {
            return baseUrl.substring(baseUrl.indexOf(protocolToken) + protocolToken.length());
        }
        return baseUrl;
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
        return insert(table, Insert.row(pairs));
    }

    public String insert(String table, Insert.Row row) throws IOException {
        String json = JsonHelper.buildJsonFromRow(row);
        return upsert(table, json, false);
    }

    public String insert(String table, List<Insert.Row> rows) throws IOException {
        String json = JsonHelper.buildJsonFromRow(rows);
        return upsert(table, json, false);
    }

    public String save(String table, String json) throws IOException {
        return upsert(table, json, true);
    }

    public String save(String table, Pair... pairs) throws IOException {
        return save(table, Insert.row(pairs));
    }

    public String save(String table, Insert.Row row) throws IOException {
        String json = JsonHelper.buildJsonFromRow(row);
        return upsert(table, json, true);
    }

    public String save(String table, List<Insert.Row> rows) throws IOException {
        String json = JsonHelper.buildJsonFromRow(rows);
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

    protected HttpUrl buildTableUrl(String table) {
        return new HttpUrl.Builder()
                .scheme("https")
                .host(supabaseUrl)
                .addPathSegment("rest")
                .addPathSegment("v1")
                .addPathSegment(table)
                .build();
    }

    protected HttpUrl buildTableUrl(String table, Condition condition) {
        return new HttpUrl.Builder()
                .scheme("https")
                .host(supabaseUrl)
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
