package com.eventtus.twitterapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.eventtus.twitterapp.database.tables.LocalUser;
import com.eventtus.twitterapp.models.Tweet;
import com.twitter.sdk.android.core.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * that class contains general methods used in the app
 * Created by nermin.yehia on 10/17/2016.
 */


public class TwitterAppUtils {

    /***
     * save item in share preferences
     * @param activity
     * @param name
     * @param value
     */
    public static void saveToSharedPrefs(Activity activity,String name,String value){

        SharedPreferences mPrefs = activity.getSharedPreferences(
                activity.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString(name, value);
        prefsEditor.commit();
    }

    /***
     * get item from shared preferences
     * @param activity
     * @param name
     * @return
     */
    public static String getFromSharedPrefs(Activity activity,String name){

        SharedPreferences mPrefs = activity.getSharedPreferences(
                activity.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return mPrefs.getString(name, null);
    }

    /***
     * assemble user
     * @param user
     * @return
     */
    public static LocalUser assemble(User user){
        return new LocalUser(user.name,user.screenName,user.description,user.profileImageUrl,user.profileBackgroundImageUrl,user.id);
    }

    /***
     * assemble list of followers
     * @param users
     * @return
     */
    public static List<LocalUser> assembleFollowers(List<User> users){
        List<LocalUser> localUsers = new ArrayList<>();
        for (User user:users) {
            localUsers.add(assemble(user));
        }
        return localUsers;
    }

    /***
     * assemble list of tweets
     * @param tweets
     * @return
     */
    public static List<Tweet> assembleTweets(List<com.twitter.sdk.android.core.models.Tweet> tweets){
        List<Tweet> localTweets = new ArrayList<>();
        for (com.twitter.sdk.android.core.models.Tweet tweet:tweets)
            localTweets.add( new Tweet(tweet.text,tweet.createdAt,tweet.favoriteCount));

        return localTweets;
    }
}
