package com.harium.supabase.storage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.harium.supabase.RequestDecorator;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StorageFileAPI {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final RequestDecorator requestDecorator;

    protected final String baseUrl;

    private final OkHttpClient client;

    private final Gson gson;

    private final boolean httpsEnabled;

    public StorageFileAPI(String baseUrl, boolean httpsEnabled, OkHttpClient client, Gson gson, RequestDecorator requestDecorator) {
        this.baseUrl = baseUrl;
        this.httpsEnabled = httpsEnabled;

        this.requestDecorator = requestDecorator;
        this.client = client;
        this.gson = gson;
    }

    public List<FileObject> listFiles(String bucketId) throws IOException {
        return listFiles(bucketId, new SearchOptions());
    }

    public List<FileObject> listFiles(String bucketId, SearchOptions options) throws IOException {
        String json = gson.toJson(options);

        Request.Builder requestBuilder = buildRequest("list", bucketId);
        RequestBody body = RequestBody.create(json, JSON);

        Request request = requestBuilder.post(body).build();

        ResponseBody responseBody = client.newCall(request).execute().body();

        Type fileListClass = new TypeToken<ArrayList<FileObject>>() {}.getType();
        return gson.fromJson(responseBody.string(), fileListClass);
    }

    public Bucket getBucket(String id) throws IOException {
        Request.Builder requestBuilder = buildRequest(id);
        Request request = requestBuilder.get().build();

        ResponseBody responseBody = client.newCall(request).execute().body();
        return gson.fromJson(responseBody.string(), Bucket.class);
    }

    public Bucket createBucket(String id) throws IOException {
        return createBucket(id, false);
    }

    public Bucket createBucket(String id, boolean isPublic) throws IOException {
        Bucket data = new Bucket();
        data.id = id;
        data.name = id;
        data.isPublic = isPublic;

        String json = gson.toJson(data);

        Request.Builder requestBuilder = buildRequest("");
        RequestBody body = RequestBody.create(json, JSON);

        Request request = requestBuilder.post(body).build();

        ResponseBody responseBody = client.newCall(request).execute().body();
        return gson.fromJson(responseBody.string(), Bucket.class);
    }

    public MessageResponse emptyBucket(String id) throws IOException {
        Request.Builder requestBuilder = buildRequest(id, "empty");
        RequestBody body = RequestBody.create(null, new byte[0]);

        Request request = requestBuilder.post(body).build();

        ResponseBody responseBody = client.newCall(request).execute().body();

        return gson.fromJson(responseBody.string(), MessageResponse.class);
    }

    public MessageResponse deleteBucket(String id) throws IOException {
        Request.Builder requestBuilder = buildRequest(id);
        Request request = requestBuilder.delete().build();
        ResponseBody responseBody = client.newCall(request).execute().body();

        return gson.fromJson(responseBody.string(), MessageResponse.class);
    }

    protected HttpUrl buildFileUrl(String action) {
        return buildFileUrl(action, "");
    }

    protected HttpUrl buildFileUrl(String action, String bucketId) {
        HttpUrl.Builder builder = new HttpUrl.Builder()
                .host(baseUrl)
                .addPathSegment("storage")
                .addPathSegment("v1")
                .addPathSegment("object");

        if (action != null && !action.isEmpty()) {
            builder.addPathSegment(action);
        }

        if (bucketId != null && !bucketId.isEmpty()) {
            builder.addPathSegment(bucketId);
        }

        if (httpsEnabled) {
            builder.scheme("https");
        }

        return builder.build();
    }

    protected Request.Builder buildRequest(String action) {
        HttpUrl httpUrl = buildFileUrl(action);

        Request.Builder builder = new Request.Builder();
        requestDecorator.decorate(builder);
        return builder.url(httpUrl);
    }

    protected Request.Builder buildRequest(String action, String bucketId) {
        HttpUrl httpUrl = buildFileUrl(action, bucketId);

        Request.Builder builder = new Request.Builder();
        requestDecorator.decorate(builder);
        return builder.url(httpUrl);
    }

}
