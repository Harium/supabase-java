package com.harium.supabase.storage;

import com.google.gson.annotations.SerializedName;

public class SearchOptions {

    public int limit = 100;

    public int offset = 0;

    @SerializedName("sortBy")
    public SortBy sortOptions;

    public String prefix = "";

}
