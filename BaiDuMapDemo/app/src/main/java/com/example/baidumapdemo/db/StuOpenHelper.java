package com.example.baidumapdemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class StuOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = "MySQLiteHelper";
    //数据库建表语句
    public static final String sql = "create table positioning (id integer primary key autoincrement, province, name, longitude, latitude)";
    public static final String sql1 = "create table test1 (id integer primary key autoincrement, name text(4),data text(5))";
    public StuOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);//创建数据库调用方法
    }
    /**
     * 第一次创建数据库时调用 在这方法里面可以进行建表
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate: " );
        db.execSQL(sql);
    }
    /**
     * 版本更新的时候调用
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "onUpgrade: " );
        switch (oldVersion){
            case 1:
                db.execSQL(sql1);
                break;
        }
    }

}
