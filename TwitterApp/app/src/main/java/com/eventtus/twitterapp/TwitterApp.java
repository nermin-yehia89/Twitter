package com.eventtus.twitterapp;

import android.app.Application;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

/**
 * Created by nermin.yehia on 10/18/2016.
 */

public class TwitterApp extends Application{

    private static final String TWITTER_KEY = "c0s5htY23m4NUnTOxEmiwJcMT";
    private static final String TWITTER_SECRET = "rKbIOeXn5fwWfJ62w3okYzDZVlsYwvk6WnnaR93BwSfa1ZsZs2";
    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);

        Fabric.with(this, new Twitter(authConfig));
    }
}
