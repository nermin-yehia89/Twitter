package com.eventtus.twitterapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

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
}
