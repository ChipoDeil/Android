package com.example.chipodeil.hookahfinder;

/**
 * Created by chipodeil on 30.10.2017.
 */

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface VedroServerApi {

    @GET("/coffeeshops")
    Call<List<CoffeeShops>> getCoffeeShops();

    @GET("/coffeeshops/comments/{id}")
    Call<List<Comment>> getComments(@Path("id") int id);

    @POST("/coffeeshops/comments/{id}")
    Call<Comment> addComment(@Path("id") int id, @Body NewComment body);

//    @POST("/coffeeshops/comments/{id}")
//    Call<Comment>(@Path("text") String text, @Path("shop_id") int id);

}
