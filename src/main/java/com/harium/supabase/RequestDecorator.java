package com.harium.supabase;

import okhttp3.Request;

public interface RequestDecorator {

    Request.Builder decorate(Request.Builder builder);

}
