package com.eventtus.twitterapp;

import android.app.Application;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

/**
 * Created by nermin.yehia on 10/18/2016.
 */

public class TwitterApp extends Application{

    private static final String TWITTER_KEY = "VCaDQb5qKAfqPVRHbTfzQmgyf";
    private static final String TWITTER_SECRET = "EOhRKp7MnJGtsgYHg484FmIZVdTb4a78rg77d2bHHFrd1S9veu";
    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);

        Fabric.with(this, new Twitter(authConfig));
    }
}
