package com.eventtus.twitterapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eventtus.twitterapp.Extras;
import com.eventtus.twitterapp.R;
import com.eventtus.twitterapp.database.tables.LocalUser;

/***
 * list followers fragment
 * 
 * @author nermin.yehia
 * 
 */
public class FollowerDetailsFragment extends BaseFragment  {
    private static final String TAG = FollowerDetailsFragment.class.getName();

	private LocalUser currentUser;

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
		((TextView)view.findViewById(R.id.name)).setText(currentUser.getName());

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





	}

	@Override
	public void onPause() {
		super.onPause();


	}

	@Override
	public void init() {
		super.init();
			screenName = getResources().getString(R.string.follower_details);

	}
}
