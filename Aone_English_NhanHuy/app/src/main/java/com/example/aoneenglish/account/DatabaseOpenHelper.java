package com.example.aoneenglish.account;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOpenHelper extends SQLiteAssetHelper {
    public  static final  String DBNAME = "HocTiengAnh.db";
    public  static final  int DBVERSION = 1;

    public DatabaseOpenHelper(@Nullable Context context) {
        super(context, DBNAME, null,DBVERSION);
    }

    @Override

    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("drop table if exists User ");
    }

}
