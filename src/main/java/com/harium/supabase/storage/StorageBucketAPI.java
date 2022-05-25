package com.harium.supabase.storage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.harium.supabase.RequestDecorator;
import com.harium.supabase.common.MessageResponse;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StorageBucketAPI {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final RequestDecorator requestDecorator;

    protected final String baseUrl;

    private final OkHttpClient client;

    private final Gson gson;

    private final boolean httpsEnabled;

    public StorageBucketAPI(String baseUrl, boolean httpsEnabled, OkHttpClient client, Gson gson, RequestDecorator requestDecorator) {
        this.baseUrl = baseUrl;
        this.httpsEnabled = httpsEnabled;

        this.requestDecorator = requestDecorator;
        this.client = client;
        this.gson = gson;
    }

    public List<Bucket> listBuckets() throws IOException {
        Request.Builder requestBuilder = buildBucketRequest("");
        Request request = requestBuilder.get().build();

        ResponseBody responseBody = client.newCall(request).execute().body();

        Type bucketListClass = new TypeToken<ArrayList<Bucket>>() {}.getType();
        return gson.fromJson(responseBody.string(), bucketListClass);
    }

    public Bucket getBucket(String id) throws IOException {
        Request.Builder requestBuilder = buildBucketRequest(id);
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

        Request.Builder requestBuilder = buildBucketRequest("");
        RequestBody body = RequestBody.create(json, JSON);

        Request request = requestBuilder.post(body).build();

        ResponseBody responseBody = client.newCall(request).execute().body();
        return gson.fromJson(responseBody.string(), Bucket.class);
    }

    public MessageResponse emptyBucket(String id) throws IOException {
        Request.Builder requestBuilder = buildBucketRequest(id, "empty");
        RequestBody body = RequestBody.create(null, new byte[0]);

        Request request = requestBuilder.post(body).build();

        ResponseBody responseBody = client.newCall(request).execute().body();

        return gson.fromJson(responseBody.string(), MessageResponse.class);
    }

    public MessageResponse deleteBucket(String id) throws IOException {
        Request.Builder requestBuilder = buildBucketRequest(id);
        Request request = requestBuilder.delete().build();
        ResponseBody responseBody = client.newCall(request).execute().body();

        return gson.fromJson(responseBody.string(), MessageResponse.class);
    }

    protected HttpUrl buildBucketUrl(String bucketId) {
        return buildBucketUrl(bucketId, "");
    }

    protected HttpUrl buildBucketUrl(String bucketId, String action) {
        HttpUrl.Builder builder = new HttpUrl.Builder()
                .host(baseUrl)
                .addPathSegment("storage")
                .addPathSegment("v1")
                .addPathSegment("bucket");

        if (bucketId != null && !bucketId.isEmpty()) {
            builder.addPathSegment(bucketId);
        }

        if (action != null && !action.isEmpty()) {
            builder.addPathSegment(action);
        }

        if (httpsEnabled) {
            builder.scheme("https");
        }

        return builder.build();
    }

    protected Request.Builder buildBucketRequest(String bucketId) {
        HttpUrl httpUrl = buildBucketUrl(bucketId);

        Request.Builder builder = new Request.Builder();
        requestDecorator.decorate(builder);
        return builder.url(httpUrl);
    }

    protected Request.Builder buildBucketRequest(String bucketId, String action) {
        HttpUrl httpUrl = buildBucketUrl(bucketId, action);

        Request.Builder builder = new Request.Builder();
        requestDecorator.decorate(builder);
        return builder.url(httpUrl);
    }

}
