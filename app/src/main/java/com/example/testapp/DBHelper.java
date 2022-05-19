package com.example.testapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "email text,"
                + "passw text,"
                + "admin integer"
                + ");");
        db.execSQL("create table film ("
                + "id integer primary key autoincrement,"
                + "title text,"
                + "is_look integer,"
                + "mark integer,"
                + "description text"
                + ");");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
