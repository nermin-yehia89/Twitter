package com.eventtus.twitterapp.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.eventtus.twitterapp.Extras;
import com.eventtus.twitterapp.services.TwitterIntentService;

/**
 * this fragment contains error receiver aslo
 */
public class BaseFragment extends Fragment {
    public static final String TAG = BaseFragment.class.getName();

    public Activity activity;
    public String screenName;
    public static ProgressDialog progressDialog;
    public ErrorReceiver errorReceiver;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    /***
     * show progress dialog with the default message
     */
    protected void showProgressDialog() {
        // create progress dialog if not created
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(activity);
            progressDialog.setTitle("Processing...");
            progressDialog.setMessage("Please wait.");
            progressDialog.setCancelable(true);
            progressDialog.setIndeterminate(true);

        }
        try {
            if (progressDialog != null && !progressDialog.isShowing())
                progressDialog.show();
        } catch (Exception e) {

            Log.d(TAG, "error : " + e);
        }

    }

    protected void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }


    public BaseFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


    public void init() {

    }


    @Override
    public void onResume() {
        super.onResume();
        init();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(screenName);
        activity.invalidateOptionsMenu();

        errorReceiver = new ErrorReceiver();
        activity.registerReceiver(errorReceiver, new IntentFilter(
                TwitterIntentService.Intents.SHOW_ERROR_DIALOG));

    }

    @Override
    public void onPause() {
        super.onPause();
        activity.unregisterReceiver(errorReceiver);
        activity.invalidateOptionsMenu();
    }

    protected void onErrorOccured() {

    }

    private class ErrorReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d(TAG,
                    "base registration onReceive ErrorReceiver"
                            + intent.getAction());

            dismissProgressDialog();
            onErrorOccured();

            String msg = (String) intent.getExtras().get(
                    Extras.ERROR_MESSAGE);

            Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();


        }
    }
}
