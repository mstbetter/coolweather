package com.coolweather.app.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.coolweather.app.db.CoolWeatherOpenHelper;
import com.coolweather.app.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tyj on 2016/11/18.
 */
public class CoolWeatherDB {
    /**
     * 数据库名
     */
    public  static final String DB_NAME="cool_weather";
    /**
     * 数据库版本
     */
    public  static final int VERSION=1;
    private static CoolWeatherDB coolWeatherDB;
    private  SQLiteDatabase db;

    /**
     * @des 将构造方法私有化
     * @param context
     */
    private  CoolWeatherDB(Context context){

        CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context, DB_NAME, null, VERSION);
         db =  dbHelper.getWritableDatabase();
    }

    /**
     * 获取 CoolWeatherDB实例
     * @param context
     * @return
     */
     public synchronized static CoolWeatherDB getInstance ( Context context){
         if (coolWeatherDB==null){
             coolWeatherDB = new CoolWeatherDB(context);
         }

         return   coolWeatherDB;

     }


    /**
     * 将Province实例储存到数据库
     */
    public void saveProvince(Province province){

        if (province!=null){
            ContentValues contentValues = new ContentValues();
            contentValues.put("province_name",province.getProvinceName());
            contentValues.put("province_code",province.getProvinceCode());
           db.insert("Province",null,contentValues);

        }
    }
    /**
     * 从数据库中读取全国所有省份的信息
     */
   public List<Province> loadProvince(){

       ArrayList<Province> provinceArrayList = new ArrayList<>();
       Cursor cursor = db.query("Province", null, null, null, null, null, null);
       if (cursor.moveToFirst()){
           do {
               Province province = new Province();
               province.setId(cursor.getColumnIndex("id"));
               province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
               province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_code")));
               provinceArrayList.add(province);
           }while(cursor.moveToNext());
       }


       return provinceArrayList;
   }


}
