package com.harium.supabase.storage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.harium.supabase.RequestDecorator;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StorageAPI {

    private final RequestDecorator requestDecorator;

    protected final String baseUrl;

    protected final OkHttpClient client;

    private final Gson gson = new Gson();

    protected boolean httpsEnabled = true;

    public StorageAPI(String baseUrl, RequestDecorator requestDecorator) {
        this.baseUrl = extractHost(baseUrl);
        this.requestDecorator = requestDecorator;

        client = new OkHttpClient();
    }

    private String extractHost(String baseUrl) {
        final String protocolToken = "://";

        if (baseUrl.startsWith("http")) {
            return baseUrl.substring(baseUrl.indexOf(protocolToken) + protocolToken.length());
        }
        return baseUrl;
    }

    public List<Bucket> listBuckets() throws IOException {
        Request.Builder requestBuilder = buildRequest("");
        Request request = requestBuilder.get().build();

        ResponseBody responseBody = client.newCall(request).execute().body();

        Type bucketListClass = new TypeToken<ArrayList<Bucket>>() {}.getType();
        return gson.fromJson(responseBody.string(), bucketListClass);
    }

    protected HttpUrl buildBucketUrl(String bucketId) {
        HttpUrl.Builder builder = new HttpUrl.Builder()
                .host(baseUrl)
                .addPathSegment("storage")
                .addPathSegment("v1")
                .addPathSegment("bucket");

        if (bucketId != null && !bucketId.isEmpty()) {
            builder.addPathSegment(bucketId);
        }

        if (httpsEnabled) {
            builder.scheme("https");
        }

        return builder.build();
    }

    protected Request.Builder buildRequest(String bucketId) {
        HttpUrl httpUrl = buildBucketUrl(bucketId);

        Request.Builder builder = new Request.Builder();
        requestDecorator.decorate(builder);
        return builder.url(httpUrl);
    }

}
