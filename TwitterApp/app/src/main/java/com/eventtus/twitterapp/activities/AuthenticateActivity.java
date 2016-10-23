package com.eventtus.twitterapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.eventtus.twitterapp.Extras;
import com.eventtus.twitterapp.R;
import com.eventtus.twitterapp.TwitterAppUtils;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

/***
 * Authentication activity
 */
public class AuthenticateActivity extends AppCompatActivity {

    private TwitterLoginButton loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);

        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                TwitterSession session = result.data;
                Toast.makeText(AuthenticateActivity.this,getResources().getString(R.string.authenticated_successfully),Toast.LENGTH_LONG).show();
                TwitterAppUtils.saveToSharedPrefs(AuthenticateActivity.this, Extras.TWITTER_USER_ID,session.getUserId()+"");
                Intent intent = new Intent(AuthenticateActivity.this, MainActivity.class);
                AuthenticateActivity.this.startActivity(intent);
                AuthenticateActivity.this.finish();
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
                Toast.makeText(AuthenticateActivity.this,getResources().getString(R.string.authentication_failed) + ": " + exception.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

}
