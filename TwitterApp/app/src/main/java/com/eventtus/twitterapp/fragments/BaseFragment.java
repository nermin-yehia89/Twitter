package com.eventtus.twitterapp.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class BaseFragment extends Fragment  {
    public static final String TAG = BaseFragment.class.getName();

    public Activity activity;
    public String screenName;
    // progress dialog
    public static ProgressDialog progressDialog;






    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    /***
     * show progress dialog with custom message
     *
     * @param msg
     */
    public void showProgressDialog(String msg) {
        // create progress dialog if not created
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(activity);
            progressDialog.setTitle("Processing...");
            progressDialog.setMessage(msg);
            progressDialog.setCancelable(true);
            progressDialog.setIndeterminate(true);

        } else {
            // set progress message
            progressDialog.setMessage(msg);
        }
        try {
            if (progressDialog != null && !progressDialog.isShowing())
                progressDialog.show();
        } catch (Exception e) {

            Log.d(TAG, "error : " + e);
        }

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


    public void init(){

    }


    @Override
    public void onResume() {
        super.onResume();


        init();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(screenName);

        activity.invalidateOptionsMenu();

    }

    @Override
    public void onPause() {
        super.onPause();

    }
}
