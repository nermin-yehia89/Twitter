package com.eventtus.twitterapp.models;

import com.eventtus.twitterapp.database.tables.LocalUser;
import com.twitter.sdk.android.core.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nermin.yehia on 10/23/2016.
 * that class to contain methods which map domain to object models
 */

public class Assembler {
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
