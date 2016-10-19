package com.eventtus.twitterapp.database.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by nermin.yehia on 10/18/2016.
 */
@DatabaseTable(tableName = "LocalUser")
public class LocalUser implements Serializable{

    public static final String ID = "Id";
    public static final String NAME = "name";
    public static final String SCREEN_NAME = "screen_name";
    public static final String DESCRIPTION = "description";
    public static final String PROFILE_IMAGE_URL = "profileImageUrl";
    public static final String PROFILE_BG_IMAGE_URL = "profileBGImageUrl";

    public LocalUser(){

    }

    public LocalUser(String name,String screenName,String description,String imageUrl,String bGImageUrl,long userId) {
       this.userId = userId;
        this.description = description;
        this.name = name;
        this.profileImageUrl = imageUrl;
        this.screenName = screenName;
        this.profileBGImageUrl = bGImageUrl;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    /** The id. */
    @DatabaseField(id = true, index = true)
    private long userId;

    @DatabaseField(columnName = NAME)
    String name;

    @DatabaseField(columnName = SCREEN_NAME)
    String screenName;

    @DatabaseField(columnName = DESCRIPTION)
    String description;

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DatabaseField(columnName = PROFILE_IMAGE_URL)
    String profileImageUrl;

    public String getProfileBGImageUrl() {
        return profileBGImageUrl;
    }

    public void setProfileBGImageUrl(String profileBGImageUrl) {
        this.profileBGImageUrl = profileBGImageUrl;
    }

    @DatabaseField(columnName = PROFILE_BG_IMAGE_URL)
    String profileBGImageUrl;
}
