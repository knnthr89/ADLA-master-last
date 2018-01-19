package com.example.dev.saludmock.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dev.saludmock.models.Users;

/**
 * Created by dev on 8/29/17.
 */

public class DBHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "users.db";
    private static final String TABLE_NAME = "  users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "pass";
    SQLiteDatabase db;

private static final String TABLE_CREATE = " create table users (id integer primary key not null, " + " name text not null, email text not null, username text not null, pass text not null);";


    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      db.execSQL(TABLE_CREATE);
        this.db = db;
    }

    public void insertContact(Users u){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "select * from users";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        values.put(COLUMN_ID, count);
        values.put(COLUMN_NAME, u.getName());
        values.put(COLUMN_EMAIL, u.getEmail());
        values.put(COLUMN_USERNAME, u.getUsername());
        values.put(COLUMN_PASSWORD, u.getPassword());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public String searchUsername(String username){
        db = this.getReadableDatabase();
        String query = "select username, pass from " +TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String a, b;
        a = "not found";
        if (cursor.moveToFirst()) {
            do{
                a = cursor.getString(0);

                if (a.equals(username)){
                    b = cursor.getString(1);
                    break;
                }
            }while (cursor.moveToNext());
        }
        return a;
    }

    public String searchPass(String username){
        db = this.getReadableDatabase();
        String query = "select username, pass from " +TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String a, b;
        b = "not found";
        if (cursor.moveToFirst()) {
            do{
                  a = cursor.getString(0);

                if (a.equals(username)){
                   b = cursor.getString(1);
                    break;
                }
            }while (cursor.moveToNext());
        }
        return b;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
     String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }
}
