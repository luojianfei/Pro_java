package com.leo.pro.app.utils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.leo.pro.app.Constant.DATABASE_NAME;
import static com.leo.pro.app.Constant.DATABASE_VERSION;

/**
 * 创建人 LEO
 * 创建时间 2019/2/14 13:50
 */

public class CustomSQLiteOpenHelper extends SQLiteOpenHelper {

    public CustomSQLiteOpenHelper(Context context){
        this(context,DATABASE_NAME,null,DATABASE_VERSION) ;
    }

    public CustomSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        getWritableDatabase() ;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(""); //如果没有表创建
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
