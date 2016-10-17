package com.eventtus.twitterapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class AuthenticateActivity extends AppCompatActivity {
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
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
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                TwitterSession session = result.data;
                // TODO: Remove toast and use the TwitterSession's userID
                // with your app's user model
                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();


//                MyTwitterApiClient apiclients=new MyTwitterApiClient(session);
//                apiclients.getCustomService().list(session.getUserId(), new Callback<Response>() {
//
//                    @Override
//                    public void failure(TwitterException arg0) {
//                        // TODO Auto-generated method stub
//
//                    }
//
//                    @Override
//                    public void success(Result<Response> arg0) {
//                                                System.out.println("Response is>>>>>>>>>"+arg0.response.body());
//
//                        // TODO Auto-generated method stub
////                        BufferedReader reader = null;
////                        StringBuilder sb = new StringBuilder();
////                        try {
////
////                            reader = new BufferedReader(new InputStreamReader(arg0.response.body()));
////
////                            String line;
////
////                            try {
////                                while ((line = reader.readLine()) != null) {
////                                    sb.append(line);
////                                }
////                            } catch (IOException e) {
////                                e.printStackTrace();
////                            }
////                        } catch (IOException e) {
////                            e.printStackTrace();
////                        }
//
//
////                        String result = sb.toString();
////                        System.out.println("Response is>>>>>>>>>"+result);
////                        try {
////                            JSONObject obj=new JSONObject(result);
////                            JSONArray ids=obj.getJSONArray("ids");
////                            //This is where we get ids of followers
////                            for(int i=0;i<ids.length();i++){
////                                System.out.println("Id of user "+(i+1)+" is "+ids.get(i));
////                            }
////                        } catch (JSONException e) {
////                            // TODO Auto-generated catch block
////                            e.printStackTrace();
////                        }
//                    }
//
//                });



                retrofit2.Call<Followers> dd =  new MyTwitterApiClient(session).getCustomService().list(session.getUserId());

                dd.enqueue(new Callback<Followers>() {
                    @Override
                    public void success(Result<Followers> result) {
                        System.out.println("Response is>>>>>>>>>"+result.data.users.size());

                    }

                    @Override
                    public void failure(TwitterException exception) {
                        System.out.println("Response is>>>>>>>>> failed" );


                    }
                });

                TwitterAppUtils.saveToSharedPrefs(AuthenticateActivity.this,Extras.TWITTER_USER_ID,session.getUserId()+"");
                Intent intent = new Intent(AuthenticateActivity.this, MainActivity.class);
                AuthenticateActivity.this.startActivity(intent);
                AuthenticateActivity.this.finish();
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
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
