package com.eventtus.twitterapp.interfaces;

import com.eventtus.twitterapp.models.Followers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by nermin.yehia on 10/18/2016.
 */

public interface CustomService {
    //    @GET("/1.1/followers/ids.json")
//    void list(@Query("user_id") long id, Callback<Response> cb);
//    @GET("/1.1/followers/list.json")
//    void show(@Query("user_id") Long userId, @Query("screen_name") String
//            var, @Query("skip_status") Boolean var1, @Query("include_user_entities") Boolean var2, @Query("count") Integer var3, Callback<Followers> cb);
    @GET("/1.1/followers/list.json")
     Call<Followers> list(@Query("user_id") long id);
//    Call<Void> list(@Query("user_id") long id, Callback<Followers> cb);
}
