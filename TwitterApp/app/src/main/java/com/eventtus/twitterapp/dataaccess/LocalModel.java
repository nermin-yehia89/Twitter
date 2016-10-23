package com.eventtus.twitterapp.dataaccess;

import android.content.Context;
import android.util.Log;

import com.eventtus.twitterapp.database.DatabaseHelper;
import com.eventtus.twitterapp.database.DatabaseHelperManager;
import com.eventtus.twitterapp.database.tables.LocalUser;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Local model is the main interface to the local data access layer, exposing
 * CRUD methods for domain objects, but abstracting the underlying data store
 * implementation.
 *
 * @author nermin.yehia
 */
public class LocalModel {


    private DatabaseHelper db;
    private static LocalModel localModel;

    public LocalModel(DatabaseHelper db) {
        this.db = db;
    }

    public static LocalModel getInstance(Context context) {
        if (localModel == null) {
            localModel = new LocalModel(
                    DatabaseHelperManager.getHelper(context));
        }
        return localModel;
    }

    /***
     * insert twitter users
     * @param localUsers
     * @return
     */
    public boolean insertTwitterUsers(List<LocalUser> localUsers) {
        try {
            db.getUserDao().deleteBuilder().delete();
            for (LocalUser localUser:localUsers)
            db.getUserDao().createOrUpdate(localUser);

            return true;
        } catch (SQLException e) {

            Log.e("insertTwitterUsers", "insertTwitterUsers :" + e.getMessage());
            return false;
        }
    }

    /***
     * select twitter users from local database
     * @return
     */
    public List<LocalUser> finfLocalUsers(){
        List<LocalUser> indicators = new ArrayList<>();
        try {
            indicators = db.getUserDao().queryForAll();
        } catch (SQLException e) {
            Log.e("finfLocalUsers", "finfLocalUsers :" + e.getMessage());
        }
        return indicators;
    }


}
