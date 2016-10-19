package com.eventtus.twitterapp.fragments;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eventtus.twitterapp.Extras;
import com.eventtus.twitterapp.R;
import com.eventtus.twitterapp.adapters.TweetsAdapter;
import com.eventtus.twitterapp.database.tables.LocalUser;
import com.eventtus.twitterapp.models.Tweet;
import com.eventtus.twitterapp.models.Tweets;
import com.eventtus.twitterapp.services.TwitterIntentService;

import java.util.ArrayList;
import java.util.List;

/***
 * list followers fragment
 * 
 * @author nermin.yehia
 * 
 */
public class FollowerDetailsFragment extends BaseFragment  {
    private static final String TAG = FollowerDetailsFragment.class.getName();

	private LocalUser currentUser;
	private TweetsReceiver tweetsReceiver;
	private ListView list;
	private List<Tweet> tweetsList = new ArrayList<Tweet>();
	private TweetsAdapter tweetsAdapter;

	public static FollowerDetailsFragment createInstance(LocalUser user) {
		FollowerDetailsFragment f = new FollowerDetailsFragment();
		Bundle bdl = new Bundle(2);

		bdl.putSerializable(Extras.FOLLWER, user);
		f.setArguments(bdl);
		return f;
	}

	@Override
	public void onAttach(Context context) {
		this.activity = (Activity) context;
		super.onAttach(context);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
							 @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_follower_details,
				container, false);

		ImageView image = (ImageView)view.findViewById(R.id.profile_img);
		ImageView imageBG = (ImageView)view.findViewById(R.id.profile_bg);

		Glide
				.with(activity)
				.load(currentUser.getProfileImageUrl())
				.placeholder(R.drawable.user_profile_placeholder)
				.into(image);


		Glide
				.with(activity)
				.load(currentUser.getProfileBGImageUrl())
				.crossFade()

				.into(imageBG);

		((TextView) view.findViewById(R.id.followeName)).setText(currentUser.getName());
		list = (ListView) view.findViewById(R.id.list);
		list.setDividerHeight(2);
		list.setEmptyView(view.findViewById(R.id.noTweets));


		tweetsAdapter = new TweetsAdapter(getActivity(), tweetsList);

		list.setAdapter(tweetsAdapter);

		return view;
	}





	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments() != null && getArguments().containsKey(Extras.FOLLWER))
			this.currentUser = (LocalUser) getArguments().getSerializable(Extras.FOLLWER);


	}

	@Override
	public void onResume() {
		super.onResume();
		tweetsReceiver = new TweetsReceiver();
		activity.registerReceiver(tweetsReceiver, new IntentFilter(
				TwitterIntentService.GOT_TWEETS));

		requestUserTweets();

	}

	private void requestUserTweets(){
		showProgressDialog();
		TwitterIntentService.startGetTweets(activity,currentUser.getScreenName());

	}


	@Override
	public void onPause() {
		super.onPause();

		activity.unregisterReceiver(tweetsReceiver);


	}

	@Override
	public void init() {
		super.init();
			screenName = getResources().getString(R.string.follower_details);
		if(currentUser != null)
			screenName += " : "+ currentUser.getScreenName();

	}


	private class TweetsReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {

			Log.d(TAG,
					"base registration onReceive ErrorReceiver"
							+ intent.getAction());

			Tweets tweets = (Tweets) intent.getExtras().getSerializable(Extras.TWEETS);
			tweetsList.clear();
			tweetsList.addAll(tweets.tweets);
			tweetsAdapter.notifyDataSetChanged();
			dismissProgressDialog();


		}
	}
}
