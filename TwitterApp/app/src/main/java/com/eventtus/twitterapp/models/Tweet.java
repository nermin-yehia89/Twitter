package com.eventtus.twitterapp.models;

import java.io.Serializable;

/**
 * Created by nermin.yehia on 10/18/2016.
 */
public class Tweet implements Serializable{

    public Tweet(){

    }

    public Tweet(String text, String createdAt, int count) {
        this.favoriteCount = count;
        this.text = text;
        this.createdAt = createdAt;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    /** The id. */
    private long userId;

    String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    String createdAt;
    Integer favoriteCount;



}
