package com.eventtus.twitterapp.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.eventtus.twitterapp.Extras;
import com.eventtus.twitterapp.MyTwitterApiClient;
import com.eventtus.twitterapp.R;
import com.eventtus.twitterapp.dataaccess.LocalModel;
import com.eventtus.twitterapp.database.tables.LocalUser;
import com.eventtus.twitterapp.models.Assembler;
import com.eventtus.twitterapp.models.Followers;
import com.eventtus.twitterapp.models.LocalUsers;
import com.eventtus.twitterapp.models.Tweets;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class TwitterIntentService extends IntentService {
    private static final String TAG = TwitterIntentService.class.getName();

    public static final String GET_TWEETS = "com.eventtus.twitterapp.services.action.getTweets";
    public static final String GOT_TWEETS = "com.eventtus.twitterapp.services.action.gotTweets";
    public static final String GET_FOLLOWERS = "com.eventtus.twitterapp.services.action.getFollowers";
    public static final String GOT_FOLLOWERS = "com.eventtus.twitterapp.services.action.gotFollowers";


    // Intent keys for this service
    public static final class Intents {
        public static final String SCREEN_NAME = "com.gi.c2do.action.ScreenName";
        public static final String USER_ID = "com.gi.c2do.action.UserID";
        public static final String SHOW_ERROR_DIALOG = "com.gi.c2do.action.ShowError";
        public static final String PAGE = "com.gi.c2do.action.Page";
        public static final String CURSOR = "com.gi.c2do.action.Cursor";

    }

    public TwitterIntentService() {
        super("TwitterIntentService");
    }

    /***
     * start service to request followers
     *
     * @param context
     * @param userId
     */
    public static void startGetFollwersService(Context context, long userId, String cursor) {
        Intent intent = new Intent(context, TwitterIntentService.class);
        intent.setAction(GET_FOLLOWERS);
        intent.putExtra(Intents.USER_ID, userId);
        intent.putExtra(Intents.CURSOR, cursor);
        context.startService(intent);
    }

    /***
     * start service to get user tweets
     *
     * @param context
     * @param screenName
     */
    public static void startGetTweets(Context context, String screenName) {
        Intent intent = new Intent(context, TwitterIntentService.class);
        intent.setAction(GET_TWEETS);
        intent.putExtra(Intents.SCREEN_NAME, screenName);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();

            if (GET_FOLLOWERS.equals(action)) {
                final long userID = intent.getLongExtra(Intents.USER_ID, 0);
                final int page = intent.getIntExtra(Intents.PAGE, 1);
                final String cursor = intent.getStringExtra(Intents.CURSOR);

                if (!"0".equals(cursor)) {
                    TwitterSession twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
                    retrofit2.Call<Followers> call = new MyTwitterApiClient(twitterSession).getCustomService().listFollowers(userID,20,cursor);

                    call.enqueue(new Callback<Followers>() {
                        @Override
                        public void success(Result<Followers> result) {
                            List<LocalUser> users = Assembler.assembleFollowers(result.data.users);
                            LocalUsers localUsers = new LocalUsers(users);
                            // save followers only for first page
                            if ("-1".equals(cursor) )
                                saveFollwers(localUsers);

                            // send broad cast to screen with data
                            Intent broadcastReceiver = new Intent(GOT_FOLLOWERS);
                            broadcastReceiver.putExtra(Extras.USERS, localUsers);
                            broadcastReceiver.putExtra(Extras.CURSOR, result.data.cursor);
                            sendBroadcast(broadcastReceiver);
                        }

                        @Override
                        public void failure(TwitterException exception) {
                            exception.printStackTrace();

                            sendErrorBroadcast(getResources().getString(R.string.followers_error) + ": " + exception.getMessage());


                        }
                    });
                }
            } else if (GET_TWEETS.equals(action)) {
                final String screenName = intent.getStringExtra(Intents.SCREEN_NAME);

                TwitterSession twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
                retrofit2.Call<List<Tweet>> call = new MyTwitterApiClient(twitterSession).getCustomService().listTweets(screenName, 10);

                call.enqueue(new Callback<List<Tweet>>() {
                    @Override
                    public void success(Result<List<Tweet>> result) {
                        List<com.eventtus.twitterapp.models.Tweet> tweets = Assembler.assembleTweets(result.data);
                        Tweets allTweets = new Tweets(tweets);

                        // send broad cast to screen with data
                        Intent broadcastReceiver = new Intent(GOT_TWEETS);
                        broadcastReceiver.putExtra(Extras.TWEETS, allTweets);
                        sendBroadcast(broadcastReceiver);

                    }

                    @Override
                    public void failure(TwitterException exception) {
                        exception.printStackTrace();

                        sendErrorBroadcast(getResources().getString(R.string.load_tweets_failed) + ": " + exception.getMessage());

                    }
                });
            }

        }
    }




    /***
     * save followers in database
     *
     * @param users
     */

    private void saveFollwers(LocalUsers users) {
        LocalModel.getInstance(getBaseContext()).insertTwitterUsers(users.users);

    }

    /***
     * send broad cast to notify screen with error
     *
     * @param msg
     */
    private void sendErrorBroadcast(String msg) {
        Intent broadcastReceiver = new Intent(Intents.SHOW_ERROR_DIALOG);
        broadcastReceiver.putExtra(Extras.ERROR_MESSAGE, msg);
        sendBroadcast(broadcastReceiver);
    }


}
