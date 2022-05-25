package com.harium.supabase.storage;

import com.google.gson.Gson;
import com.harium.supabase.RequestDecorator;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.util.List;

public class StorageAPI {

    private final RequestDecorator requestDecorator;

    protected final String baseUrl;

    private final OkHttpClient client;

    private final Gson gson;

    protected boolean httpsEnabled = true;

    private final StorageBucketAPI storageBucketAPI;

    private final StorageFileAPI storageFileAPI;

    public StorageAPI(String baseUrl, RequestDecorator requestDecorator) {
        this.baseUrl = extractHost(baseUrl);
        this.requestDecorator = requestDecorator;

        client = new OkHttpClient();
        gson = new Gson();

        storageBucketAPI = new StorageBucketAPI(this.baseUrl, httpsEnabled, client, gson, requestDecorator);
        storageFileAPI = new StorageFileAPI(this.baseUrl, httpsEnabled, client, gson, requestDecorator);
    }

    private String extractHost(String baseUrl) {
        final String protocolToken = "://";

        if (baseUrl.startsWith("http")) {
            return baseUrl.substring(baseUrl.indexOf(protocolToken) + protocolToken.length());
        }
        return baseUrl;
    }

    public List<Bucket> listBuckets() throws IOException {
        return storageBucketAPI.listBuckets();
    }

    public Bucket getBucket(String id) throws IOException {
        return storageBucketAPI.getBucket(id);
    }

    public Bucket createBucket(String id) throws IOException {
        return storageBucketAPI.createBucket(id);
    }

    public Bucket createBucket(String id, boolean isPublic) throws IOException {
        return storageBucketAPI.createBucket(id, isPublic);
    }

    public MessageResponse emptyBucket(String id) throws IOException {
        return storageBucketAPI.emptyBucket(id);
    }

    public MessageResponse deleteBucket(String id) throws IOException {
        return storageBucketAPI.deleteBucket(id);
    }


    public List<FileObject> listFiles(String bucketId) throws IOException {
        return storageFileAPI.listFiles(bucketId);
    }

    public List<FileObject> listFiles(String bucketId, SearchOptions options) throws IOException {
        return storageFileAPI.listFiles(bucketId, options);
    }
}
