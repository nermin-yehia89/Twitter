package com.eventtus.twitterapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eventtus.twitterapp.R;
import com.eventtus.twitterapp.models.Tweet;

import java.util.List;


/***
 * that adapter used to show Conversations list and can be used for other data
 * 
 * @author nermin.yehia
 * 
 */
public class TweetsAdapter extends BaseAdapter {

	private Activity context;
	private List<Tweet> data;
	private SparseBooleanArray mSelectedItemsIds;

	private class ViewHolder {
		TextView createaAt;
		TextView text;
	}

	public TweetsAdapter(Context context, List<Tweet> objects) {
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
			convertView = vi.inflate(R.layout.tweetr_row, null);
			holder = new ViewHolder();
			holder.createaAt = (TextView) convertView
					.findViewById(R.id.createdAt);
			holder.text = (TextView) convertView
					.findViewById(R.id.text);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Tweet follower = (Tweet) data.get(position);

		holder.text.setText(follower.getText());
		holder.createaAt.setText(follower.getCreatedAt());


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
