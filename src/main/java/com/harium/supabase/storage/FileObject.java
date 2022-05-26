package com.harium.supabase.storage;

import com.google.gson.annotations.SerializedName;

public class FileObject {

    public String name;

    public String id;

    @SerializedName("created_at")
    public String createdAt;

    @SerializedName("updated_at")
    public String updatedAt;

    @SerializedName("last_accessed_at")
    public String lastAccessedAt;

    @SerializedName("metadata")
    public Metadata metadata;

}
