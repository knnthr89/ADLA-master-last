package com.example.dev.saludmock.db;

import android.support.v7.app.AppCompatActivity;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;

import java.io.IOException;

/**
 * Created by kennethrizo on 19/11/17.
 */

public class ManagerCouch extends AppCompatActivity{

    //Create a manager
    private Manager manager = null;
    private Database database = null;

    public void manager() {

        try{
            manager = new Manager(new AndroidContext(getApplicationContext()), Manager.DEFAULT_OPTIONS);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void database() {

        try {
            database = manager.getDatabase("adla");
        }catch (CouchbaseLiteException e){
            e.printStackTrace();
        }
    }

  /*  public void document(){
        int numero = (int) (Math.random() * 10000000) + 1;
        String numeroString = Integer.toString(numero);

        //Create a new document
        Document document = database.getDocument(numeroString);
        //     UnsavedRevision revision = document.createRevision();
        //    revision.setUserProperties(properties);
        //Save the document to the database
        try {
           document.putProperties(properties);
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }*/
}
