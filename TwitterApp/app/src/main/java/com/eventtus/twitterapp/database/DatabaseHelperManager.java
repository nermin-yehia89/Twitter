/**
 * 
 */
package com.eventtus.twitterapp.database;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

/***
 * that class to make a singleton of the database helper to access database
 * @author nermin.yehia
 *
 */
public class DatabaseHelperManager {
	private static DatabaseHelper dbHelper = null;
	private static int helperCount = 1;

	/***
	 * database helper
	 * @param context
	 * @return
	 */
	public static DatabaseHelper getHelper(Context context) {
		if (dbHelper == null) {
			dbHelper = (DatabaseHelper) OpenHelperManager.getHelper(context, DatabaseHelper.class);
		}
		helperCount++;

		return dbHelper;
	}

	public static void releaseHelper() {
		if (--helperCount == 0 && dbHelper != null) {
			OpenHelperManager.releaseHelper();
			dbHelper = null;
		}
	}
}
