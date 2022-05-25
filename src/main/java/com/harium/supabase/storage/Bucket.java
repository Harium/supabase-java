package com.harium.supabase.storage;

import com.google.gson.annotations.SerializedName;

public class Bucket {

    public String id;

    public String name;

    public String owner;

    @SerializedName(value = "public")
    public boolean isPublic;

    @SerializedName(value = "created_at")
    public String createdAt;

    @SerializedName(value = "updated_at")
    public String updatedAt;

}
