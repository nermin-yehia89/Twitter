package com.eventtus.twitterapp.models;

import com.eventtus.twitterapp.database.tables.LocalUser;

import java.io.Serializable;
import java.util.List;

/**
 * Created by nermin.yehia on 10/17/2016.
 */

public class LocalUsers implements Serializable{
    public final List<LocalUser> users;
    public LocalUsers(List<LocalUser> users) {
        this.users = users;
    }
}
