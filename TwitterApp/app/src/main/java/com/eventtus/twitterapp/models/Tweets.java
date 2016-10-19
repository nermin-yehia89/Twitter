package com.eventtus.twitterapp.models;


import java.io.Serializable;
import java.util.List;

/**
 * Created by nermin.yehia on 10/17/2016.
 */

public class Tweets implements Serializable{
    public final List<Tweet> tweets;

    public Tweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }
}
