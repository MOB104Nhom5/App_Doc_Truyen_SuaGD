package com.example.app_doc_truyen.Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {
    @POST("reg")
    Call<User> posReg(@Body User user) ;
    @POST("login")
    Call<User> posLogin(@Body User user) ;
    @GET("comics")
    Call<Comic> View(@Body Comic comic);
//    @PUT("comics/")
//    Call<Comic> update(@Body Comic comic);
    @GET("comics/{id}")
    Call<Comic> getComic(@Path("id") String userId);
    @POST("comics/add")
    Call<User> posComic(@Body Comic comic ) ;
}
