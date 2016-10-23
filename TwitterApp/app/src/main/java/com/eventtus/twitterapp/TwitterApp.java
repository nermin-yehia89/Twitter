package com.eventtus.twitterapp;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.eventtus.twitterapp.database.DatabaseHelperManager;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

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
        // initialize twitter configuration
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig));
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        DatabaseHelperManager.releaseHelper();
    }
}
