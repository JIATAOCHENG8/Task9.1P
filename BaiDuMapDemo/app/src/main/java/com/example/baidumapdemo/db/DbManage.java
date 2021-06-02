package com.example.baidumapdemo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.baidumapdemo.data.PositioningInfo;

import java.util.ArrayList;
import java.util.List;


public class DbManage {

    private static final String DATABASENAME = "test.db";// 数据库名称
    private static final int DATABASEVERSION = 1;// 数据库版本
    private static DbManage dbManage;
    private static StuOpenHelper stuOpenHelper;
    private static SQLiteDatabase sqLiteDatabase;

    public static DbManage getInstance() {
        if (dbManage == null) {
            synchronized (DbManage.class) {
                if (dbManage == null) {
                    dbManage = new DbManage();
                }
            }
        }

        return dbManage;
    }

    public void init(Context context) {
        if (stuOpenHelper == null) {
            stuOpenHelper = new StuOpenHelper(context, DATABASENAME, null, DATABASEVERSION);
        }
        if (sqLiteDatabase == null) {
            sqLiteDatabase = stuOpenHelper.getWritableDatabase();
        }
    }
    public static void closeSQLiteDatabase() {
        sqLiteDatabase.close();
    }


    public void addRecording(PositioningInfo data) {

        String insert = "insert into positioning (province,name,longitude,latitude) values ('" + data.getProvince()+"',"+
                "'"+data.getName()+"',"+
                "'"+data.getLongitude()+"',"+
                "'"+data.getLatitude() + "')";
        sqLiteDatabase.execSQL(insert);
    }

    public void updateRecording(long id, String data) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("data", data);
        sqLiteDatabase.update("positioning", contentValues, "id = ?", new String[]{String.valueOf(id)});
    }

    public void deleteRecording(long id){
        String delete = new StringBuilder().append("delete from positioning where id = '").append(id).append("'").toString();
        sqLiteDatabase.execSQL(delete);
    }

    /**
     * 查询所有数据
     * @return
     */
    public List<PositioningInfo> getPositioningInfo() {
        String query = "select * from positioning";
        List<PositioningInfo> positioningInfoList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (!cursor.moveToFirst()){
            return positioningInfoList;
        }
        do{
            PositioningInfo positioningInfo = new PositioningInfo();
            String province = cursor.getString(cursor.getColumnIndex("province"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
            double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
            positioningInfo.setProvince(province);
            positioningInfo.setName(name);
            positioningInfo.setLongitude(longitude);
            positioningInfo.setLatitude(latitude);
            positioningInfoList.add(positioningInfo);
        }while (cursor.moveToNext());
        return positioningInfoList;
    }



}
