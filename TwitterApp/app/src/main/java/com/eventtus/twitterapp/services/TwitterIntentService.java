package com.eventtus.twitterapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.eventtus.twitterapp.dataaccess.LocalModel;
import com.eventtus.twitterapp.models.LocalUsers;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class TwitterIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String SAVE_USERS = "com.eventtus.twitterapp.services.action.saveUsers";

    // Intent keys for this service
    public static final class Intents {
        public static final String USERS = "com.gi.c2do.action.Users";



}
        public TwitterIntentService() {
        super("TwitterIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionSaveUsers(Context context, LocalUsers users) {
        Intent intent = new Intent(context, TwitterIntentService.class);
        intent.setAction(SAVE_USERS);
        intent.putExtra(Intents.USERS, users);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (SAVE_USERS.equals(action)) {
                final LocalUsers users = (LocalUsers)intent.getSerializableExtra(Intents.USERS);
                saveTweets(users);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void saveTweets(LocalUsers users) {
        LocalModel.getInstance(getBaseContext()).insertTwitterUsers(users.users);

    }


}
