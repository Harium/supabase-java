package com.harium.supabase.storage;

import com.google.gson.annotations.SerializedName;

public class Metadata {

    public long size;

    @SerializedName("mimetype")
    public String mimeType;

    public String cacheControl;

}
