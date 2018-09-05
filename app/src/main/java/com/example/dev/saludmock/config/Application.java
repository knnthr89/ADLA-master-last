package com.example.dev.saludmock.config;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;
import com.couchbase.lite.util.Log;

import java.io.IOException;

public class Application extends android.app.Application {

    //no se debe de usar porque no será transferencia de mensajes
    public static String TAG = "Message";
    private static String DBNAME = "adla";
    private Database database = null;
    private Manager manager;

    public Database getDatabase(){
        if (database == null) {
            try {
                manager = new Manager(new AndroidContext(this), Manager.DEFAULT_OPTIONS);
                Log.d(TAG, "Created database manager");

                if (!Manager.isValidDatabaseName(DBNAME)) {
                    Log.e(TAG, "Bad database name");
                    return null;
                }

                try {
                    database = manager.getDatabase(DBNAME);
                    database.delete();
                    database = manager.getDatabase(DBNAME);
                    Log.d(TAG, "Database created");
                } catch (CouchbaseLiteException e) {
                    Log.e(TAG, "Database creation failed");
                    return null;
                }
            } catch (IOException e) {
                Log.e(TAG, "Failed to create database manager");
                return null;
            }

        }
        return database;
    }

    @Override
    public void onCreate() {
        if (getDatabase() == null){

            Log.e(TAG, "Failed to initialize");
            return;
        }
        super.onCreate();
        }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
