package com.demoapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET("test")
    Call<List<Post>> getPosts();
}
