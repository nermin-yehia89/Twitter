package com.eventtus.twitterapp.interfaces;

import com.eventtus.twitterapp.models.Followers;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by nermin.yehia on 10/18/2016.
 */

public interface CustomService {
     @GET("/1.1/followers/list.json")
     Call<Followers> listFollowers(@Query("user_id") long id,@Query("count") int count, @Query("cursor") String cursor);
     @GET("/1.1/statuses/user_timeline.json")
     Call<List<Tweet>> listTweets(@Query("screen_name") String id ,@Query("count") Integer count);
}
