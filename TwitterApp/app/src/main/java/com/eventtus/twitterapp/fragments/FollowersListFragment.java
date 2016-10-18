package com.eventtus.twitterapp.fragments;

import android.app.Activity;
import android.content.Context;
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
import android.widget.Toast;

import com.eventtus.twitterapp.MyTwitterApiClient;
import com.eventtus.twitterapp.R;
import com.eventtus.twitterapp.TwitterAppUtils;
import com.eventtus.twitterapp.adapters.FollowersAdapter;
import com.eventtus.twitterapp.dataaccess.LocalModel;
import com.eventtus.twitterapp.database.tables.LocalUser;
import com.eventtus.twitterapp.models.Followers;
import com.eventtus.twitterapp.models.LocalUsers;
import com.eventtus.twitterapp.services.TwitterIntentService;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

import java.util.ArrayList;
import java.util.List;

/***
 * list followers fragment
 * 
 * @author nermin.yehia
 * 
 */
public class FollowersListFragment extends BaseFragment implements
		OnItemClickListener {
    private static final String TAG = FollowersListFragment.class.getName();
	private ListView list;
	private List<LocalUser> followersList = new ArrayList<LocalUser>();
	private FollowersAdapter followersAdapter;
	private FollowersLookupTaskDB followersLookupTaskDB;


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

		requestCurrentUserFollowers();
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
	 * 
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
		FollowerDetailsFragment fragment =  FollowerDetailsFragment.createInstance(user);
		getActivity().getSupportFragmentManager().beginTransaction()
				.addToBackStack(FollowerDetailsFragment.class.getName())
				.replace(R.id.page_content, fragment).commit();
	}

	private void requestCurrentUserFollowers(){
		showProgressDialog();
		TwitterSession twitterSession =  TwitterCore.getInstance().getSessionManager().getActiveSession();
		retrofit2.Call<Followers> dd =  new MyTwitterApiClient(twitterSession).getCustomService().list(twitterSession.getUserId());

		dd.enqueue(new Callback<Followers>() {
			@Override
			public void success(Result<Followers> result) {
				Log.d(TAG,"Response is>>>>>>>>>"+result.data.users.size());
				List<LocalUser> users = TwitterAppUtils.assemble(result.data.users);
				LocalUsers localUsers = new LocalUsers(users);
				followersList.clear();
				followersList.addAll(users);
				// start service to save followers
				TwitterIntentService.startActionSaveUsers(activity,localUsers);

				followersAdapter.notifyDataSetChanged();
				dismissProgressDialog();
			}

			@Override
			public void failure(TwitterException exception) {
				Toast.makeText(activity,getResources().getString(R.string.followers_error) + ": " + exception.getMessage(),Toast.LENGTH_LONG).show();
				dismissProgressDialog();


			}
		});
	}



}
