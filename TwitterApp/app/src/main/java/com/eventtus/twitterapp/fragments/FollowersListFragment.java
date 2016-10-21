package com.eventtus.twitterapp.fragments;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.eventtus.twitterapp.EndlessScrollListener;
import com.eventtus.twitterapp.Extras;
import com.eventtus.twitterapp.R;
import com.eventtus.twitterapp.adapters.FollowersAdapter;
import com.eventtus.twitterapp.dataaccess.LocalModel;
import com.eventtus.twitterapp.database.tables.LocalUser;
import com.eventtus.twitterapp.models.LocalUsers;
import com.eventtus.twitterapp.services.TwitterIntentService;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

import java.util.ArrayList;
import java.util.List;

/***
 * list followers fragment
 *
 * @author nermin.yehia
 */
public class FollowersListFragment extends BaseFragment implements
        OnItemClickListener {
    private static final String TAG = FollowersListFragment.class.getName();
    private ListView list;
    private List<LocalUser> followersList = new ArrayList<LocalUser>();
    private FollowersAdapter followersAdapter;
    private FollowersLookupTaskDB followersLookupTaskDB;
    private FollowersReceiver followersReceiver;
    private String cursor = "-1";


    @Override
    public void onAttach(Context context) {
        this.activity = (Activity) context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_followers,
                container, false);

        list = (ListView) view.findViewById(R.id.list);
        list.setDividerHeight(2);
        list.setOnItemClickListener(this);
        list.setEmptyView(view.findViewById(R.id.noFollowers));
        list.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                if( !"-1".equals(cursor))
                requestCurrentUserFollowers(cursor);
                // or customLoadMoreDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });

        followersAdapter = new FollowersAdapter(getActivity(), followersList);

        list.setAdapter(followersAdapter);

        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

        followersReceiver = new FollowersReceiver();
        activity.registerReceiver(followersReceiver, new IntentFilter(
                TwitterIntentService.GOT_FOLLOWERS));

        cursor = "-1";
        showProgressDialog();
        requestCurrentUserFollowers(cursor);
        lookupFollowers();



    }

    @Override
    public void init() {
        super.init();
        screenName = getResources().getString(R.string.followers);
    }

    @Override
    public void onPause() {
        super.onPause();

        activity.unregisterReceiver(followersReceiver);
    }

    /***
     * Executes AsyncTask for retrieving nodes
     */
    void lookupFollowers() {
        if (followersLookupTaskDB != null)
            followersLookupTaskDB.cancel(true);
        followersLookupTaskDB = new FollowersLookupTaskDB();
        followersLookupTaskDB.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);

    }


    /***
     * lookup rides task
     *
     * @author nermin.yehia
     */
    private class FollowersLookupTaskDB extends
            AsyncTask<Void, User, Void> {
        List<LocalUser> followers = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            followers.clear();


        }

        /***
         * Assures that a render is called to trigger an empty list view, if
         * there are no results
         */
        @Override
        protected void onPostExecute(Void result) {
            followersList.clear();
            followersList.addAll(followers);
            followersAdapter.notifyDataSetChanged();

        }

        @Override
        protected Void doInBackground(Void... params) {
            followers = LocalModel.getInstance(activity).findUsers();
            return null;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                            long arg3) {

        LocalUser user = followersList.get(position);
        openFollowerDetailsFragment(user);


    }


    private void openFollowerDetailsFragment(LocalUser user) {
        FollowerDetailsFragment fragment = FollowerDetailsFragment.createInstance(user);
        getActivity().getSupportFragmentManager().beginTransaction()
                .addToBackStack(FollowerDetailsFragment.class.getName())
                .replace(R.id.page_content, fragment).commit();
    }

    private void requestCurrentUserFollowers(String cursor) {

        TwitterSession twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
        TwitterIntentService.startGetFollwersService(activity, twitterSession.getUserId(),cursor);


    }

    private class FollowersReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d(TAG,
                    "base registration onReceive ErrorReceiver"
                            + intent.getAction());

            LocalUsers localUsers = (LocalUsers) intent.getExtras().getSerializable(Extras.USERS);
            if ("-1".equals(cursor) )
                followersList.clear();
            cursor = intent.getStringExtra(Extras.CURSOR);


            followersList.addAll(localUsers.users);
            followersAdapter.notifyDataSetChanged();

            dismissProgressDialog();


        }
    }

}
