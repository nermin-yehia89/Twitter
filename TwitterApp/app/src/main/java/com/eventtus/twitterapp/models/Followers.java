package com.eventtus.twitterapp.models;

import com.google.gson.annotations.SerializedName;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

/**
 * Created by nermin.yehia on 10/17/2016.
 */

public class Followers {
    @SerializedName("users")
    public final List<User> users;
    @SerializedName("next_cursor_str")
    public String cursor;

    public Followers(List<User> users) {
        this.users = users;
    }
}
