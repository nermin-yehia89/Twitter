package com.eventtus.twitterapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.eventtus.twitterapp.database.tables.LocalUser;
import com.twitter.sdk.android.core.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nermin.yehia on 10/17/2016.
 */

public class TwitterAppUtils {

    public static void saveToSharedPrefs(Activity activity,String name,String value){

        SharedPreferences mPrefs = activity.getSharedPreferences(
                activity.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString(name, value);
        prefsEditor.commit();
    }

    public static String getFromSharedPrefs(Activity activity,String name){

        SharedPreferences mPrefs = activity.getSharedPreferences(
                activity.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return mPrefs.getString(name, null);
    }

    public static LocalUser assemble(User user){
        return new LocalUser(user.name,user.screenName,user.description,user.profileImageUrl,user.id);
    }

    public static List<LocalUser> assemble(List<User> users){
        List<LocalUser> localUsers = new ArrayList<>();
        for (User user:users)
        localUsers.add( new LocalUser(user.name,user.screenName,user.description,user.profileImageUrl,user.id));

        return localUsers;
    }
}
