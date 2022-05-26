package com.harium.supabase.database;

import com.harium.postgrest.Condition;
import com.harium.postgrest.PostgrestClient;
import com.harium.supabase.RequestDecorator;
import okhttp3.HttpUrl;
import okhttp3.Request;

public class DatabaseAPI extends PostgrestClient {

    private final RequestDecorator requestDecorator;

    public DatabaseAPI(String supabaseUrl, RequestDecorator requestDecorator) {
        super(supabaseUrl);
        this.requestDecorator = requestDecorator;
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

        Request.Builder builder = new Request.Builder();
        requestDecorator.decorate(builder);
        return builder.url(httpUrl);
    }

    protected Request.Builder buildRequest(String table, Condition condition) {
        HttpUrl httpUrl = buildTableUrl(table, condition);

        Request.Builder builder = new Request.Builder();
        requestDecorator.decorate(builder);
        return builder.url(httpUrl);
    }

}
