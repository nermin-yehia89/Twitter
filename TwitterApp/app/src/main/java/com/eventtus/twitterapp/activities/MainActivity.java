package com.eventtus.twitterapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.eventtus.twitterapp.Extras;
import com.eventtus.twitterapp.R;
import com.eventtus.twitterapp.TwitterAppUtils;
import com.eventtus.twitterapp.fragments.FollowersListFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String userID = TwitterAppUtils.getFromSharedPrefs(this, Extras.TWITTER_USER_ID) ;

        if(savedInstanceState == null && userID != null )
            openFollowersFragment();


    }


    private void openFollowersFragment() {
        FollowersListFragment fragment = new FollowersListFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.page_content, fragment).commit();
    }

    @Override
    protected void onResume() {
      super.onResume();
        String userID = TwitterAppUtils.getFromSharedPrefs(this, Extras.TWITTER_USER_ID) ;
        Log.d(TAG," User ID : " + userID);
        if(userID == null){
            // open authenticate activity
            Intent intent = new Intent(this, AuthenticateActivity.class);
            startActivity(intent);
            finish();

        }
    }


}
