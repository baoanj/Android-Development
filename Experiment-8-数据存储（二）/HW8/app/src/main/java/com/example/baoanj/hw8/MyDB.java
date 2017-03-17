package com.example.baoanj.hw8;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by baoanj on 2016/11/21.
 */
public class MyDB extends SQLiteOpenHelper {
    private static final String DB_NAME = "firstdb";
    private static final String TABLE_NAME = "birthdays";
    private static final int DB_VERSION = 1;

    public MyDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "create table if not exists "
                + TABLE_NAME
                + " (id integer primary key,name TEXT,birth TEXT,gift TEXT)";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
