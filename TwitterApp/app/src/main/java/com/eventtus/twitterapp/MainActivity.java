package com.eventtus.twitterapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        String userID = TwitterAppUtils.getFromSharedPrefs(this,Extras.TWITTER_USER_ID) ;
        Log.d(TAG," User ID : " + userID);
        if(userID == null){
            // open authenticate activity
            Intent intent = new Intent(this, AuthenticateActivity.class);
            startActivity(intent);
            finish();

        }else{
            TwitterSession twitterSession =  TwitterCore.getInstance().getSessionManager().getActiveSession();
            //Twitter.getSessionManager().getSession(Long.parseLong(TwitterAppUtils.getFromSharedPrefs(this,Extras.TWITTER_USER_ID))) ;
            retrofit2.Call<Followers> dd =  new MyTwitterApiClient(twitterSession).getCustomService().list(twitterSession.getUserId());

            dd.enqueue(new Callback<Followers>() {
                @Override
                public void success(Result<Followers> result) {
                    Log.d(TAG,"Response is>>>>>>>>>"+result.data.users.size());
for (User user:result.data.users ){
    Log.d(TAG,"Response is>>>>>>>>>"+user.screenName);

}
                }

                @Override
                public void failure(TwitterException exception) {
                    System.out.println("Response is>>>>>>>>> failed" );


                }
            });
        }
    }


}
