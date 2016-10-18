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
 * implementation. Additionally, any second-level cacheing can be implemented
 * here.
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

    public boolean insertTwitterUsers(List<LocalUser> localUsers) {
        try {
            db.getUserDao().deleteBuilder().delete();
            for (LocalUser localUser:localUsers)
            db.getUserDao().createOrUpdate(localUser);

            return true;
        } catch (SQLException e) {

            Log.e("insertRole", "insertRole :" + e.getMessage());
            return false;
        }
    }

    public boolean insertTwitterUser(LocalUser localUser) {
        try {
            db.getUserDao().createOrUpdate(localUser);

            return true;
        } catch (SQLException e) {

            Log.e("insertRole", "insertRole :" + e.getMessage());
            return false;
        }
    }


    public List<LocalUser> findUsers(){
        List<LocalUser> indicators = new ArrayList<>();
        try {
            indicators = db.getUserDao().queryForAll();
        } catch (SQLException e) {
            Log.e("findLocalIndicators", "findLocalIndicators :" + e.getMessage());
        }
        return indicators;
    }


}
