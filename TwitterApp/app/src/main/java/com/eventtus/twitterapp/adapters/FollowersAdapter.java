package com.eventtus.twitterapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eventtus.twitterapp.R;
import com.eventtus.twitterapp.database.tables.LocalUser;

import java.util.List;


/***
 * that adapter used to show followers list
 * 
 * @author nermin.yehia
 * 
 */
public class FollowersAdapter extends BaseAdapter {

	private Activity context;
	private List<LocalUser> data;
	private SparseBooleanArray mSelectedItemsIds;

	private class ViewHolder {
		TextView handle;
		TextView bio;
		TextView name;
		ImageView icon;
	}

	public FollowersAdapter(Context context, List<LocalUser> objects) {
		this.data = objects;
		this.context = (Activity) context;
		mSelectedItemsIds = new SparseBooleanArray();

	}



	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		if (convertView == null) {
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.follower_row, null);
			holder = new ViewHolder();
			holder.handle = (TextView) convertView
					.findViewById(R.id.handle);
			holder.bio = (TextView) convertView
					.findViewById(R.id.bio);
			holder.name = (TextView) convertView
					.findViewById(R.id.name);
			holder.icon = (ImageView) convertView
					.findViewById(R.id.profile_img);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		LocalUser follower = (LocalUser) data.get(position);

		holder.name.setText(follower.getName());
		holder.handle.setText("@"+follower.getScreenName());
		holder.bio.setText(follower.getDescription());
		Glide
				.with(context)
				.load(follower.getProfileImageUrl())
				.centerCrop()
				.placeholder(R.drawable.user_profile_placeholder)
				.crossFade()
				.into(holder.icon);


		return convertView;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}



}
