package com.eventtus.twitterapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.eventtus.twitterapp.database.tables.LocalUser;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/***
 * that class to handle database creation
 *
 * @author nermin.yehia
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "TwitterApp.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = DatabaseHelper.class.getName();
    private Dao<LocalUser, Integer> localUserDto = null;

    /***
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, LocalUser.class);


        } catch (SQLException e) {

            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {

            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, LocalUser.class, true);

            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {

            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }


    @Override
    public void close() {
        super.close();
        localUserDto = null;

    }

    public Dao<LocalUser, Integer> getUserDao() throws SQLException {
        if (localUserDto == null) {
            localUserDto = DaoManager.createDao(getConnectionSource(),
                    LocalUser.class);
        }
        return localUserDto;
    }

    public boolean clearData() {
        boolean cleared = false;
        try {

            TableUtils.clearTable(getConnectionSource(), LocalUser.class);
            ;

            Log.d(TAG, "Remove all DB");
            cleared = true;
        } catch (SQLException e) {

            Log.e(TAG, "Remove all DB error : " + e);
        }
        return cleared;
    }

}