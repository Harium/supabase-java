package com.harium.supabase.storage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.harium.supabase.RequestDecorator;
import com.harium.supabase.common.MessageResponse;
import com.harium.supabase.storage.payload.PrefixesRequest;
import com.harium.supabase.storage.payload.UploadResponse;
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

    public byte[] download(String bucketId, String path) throws IOException {
        Request.Builder requestBuilder = buildRequest("", bucketId, path);
        Request request = requestBuilder.get().build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            return null;
        }
        ResponseBody responseBody = response.body();
        return responseBody.bytes();
    }

    public List<FileObject> remove(String bucketId, String ... paths) throws IOException {
        PrefixesRequest payload = new PrefixesRequest();
        payload.prefixes = paths;

        String json = gson.toJson(payload);

        Request.Builder requestBuilder = buildRequest("", bucketId);
        RequestBody body = RequestBody.create(json, JSON);

        Request request = requestBuilder.delete(body).build();

        ResponseBody responseBody = client.newCall(request).execute().body();

        Type fileListClass = new TypeToken<ArrayList<MessageResponse>>() {}.getType();
        return gson.fromJson(responseBody.string(), fileListClass);
    }

    public UploadResponse upload(String supabasePath, byte[] data) throws IOException {
        return upload(supabasePath, null, data, true);
    }

    public UploadResponse upload(String supabasePath, FileOptions options, byte[] data) throws IOException {
        return upload(supabasePath, options, data, true);
    }

    public UploadResponse upload(String supabasePath, FileOptions options, byte[] data, boolean inferContentType) throws IOException {
        if (options == null) {
            options = new FileOptions();
        }

        if (inferContentType) {
            //options.contentType = "";
            //options.contentType = MimeMapping.MimeUtility.GetMimeMapping(supabasePath);
        }

        return uploadOrUpdate(supabasePath, options, data);
    }

    private UploadResponse uploadOrUpdate(String path, FileOptions options, byte[] data) throws IOException {
        Request.Builder requestBuilder = buildRequest(path);
        requestBuilder.addHeader("cache-control", "max-age="+options.cacheControl);
        requestBuilder.addHeader("content-type", options.contentType);

        if (options.upsert) {
            requestBuilder.addHeader("x-upsert", Boolean.toString(options.upsert));
        }

        // Multipart format
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("", "dummy.txt", RequestBody.create(data))
                .build();

        Request request = requestBuilder.post(body).build();

        ResponseBody responseBody = client.newCall(request).execute().body();
        return gson.fromJson(responseBody.string(), UploadResponse.class);
    }

    protected HttpUrl buildFileUrl(String path) {
        HttpUrl.Builder builder = new HttpUrl.Builder()
                .host(baseUrl)
                .addPathSegment("storage")
                .addPathSegment("v1")
                .addPathSegment("object");

        String[] paths = path.split("/");
        for (String p : paths) {
            builder.addPathSegment(p);
        }

        if (httpsEnabled) {
            builder.scheme("https");
        }

        return builder.build();
    }

    protected HttpUrl buildFileUrl(String action, String bucketId, String supabasePath) {
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

        if (supabasePath != null && !supabasePath.isEmpty()) {
            builder.addPathSegment(supabasePath);
        }

        if (httpsEnabled) {
            builder.scheme("https");
        }

        return builder.build();
    }

    protected Request.Builder buildRequest(String path) {
        HttpUrl httpUrl = buildFileUrl(path);

        Request.Builder builder = new Request.Builder();
        requestDecorator.decorate(builder);
        return builder.url(httpUrl);
    }

    protected Request.Builder buildRequest(String action, String bucketId) {
        return buildRequest(action, bucketId, "");
    }

    protected Request.Builder buildRequest(String action, String bucketId, String supabasePath) {
        HttpUrl httpUrl = buildFileUrl(action, bucketId, supabasePath);

        Request.Builder builder = new Request.Builder();
        requestDecorator.decorate(builder);
        return builder.url(httpUrl);
    }

}
