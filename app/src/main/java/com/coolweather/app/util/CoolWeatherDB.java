package com.coolweather.app.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.coolweather.app.db.CoolWeatherOpenHelper;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
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
    public static final String DB_NAME = "cool_weather";
    /**
     * 数据库版本
     */
    public static final int VERSION = 1;
    private static CoolWeatherDB coolWeatherDB;
    private SQLiteDatabase db;

    /**
     * @param context
     * @des 将构造方法私有化
     */
    private CoolWeatherDB(Context context) {

        CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 获取 CoolWeatherDB实例
     *
     * @param context
     * @return coolWeatherDB
     */
    public synchronized static CoolWeatherDB getInstance(Context context) {
        if (coolWeatherDB == null) {
            coolWeatherDB = new CoolWeatherDB(context);
        }

        return coolWeatherDB;

    }


    /**
     * 将Province实例储存到数据库
     */
    public void saveProvince(Province province) {

        if (province != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("province_name", province.getProvinceName());
            contentValues.put("province_code", province.getProvinceCode());
            db.insert("Province", null, contentValues);

        }
    }

    /**
     * 从数据库中读取全国所有省份的信息
     */
    public List<Province> loadProvince() {

        ArrayList<Province> provinceArrayList = new ArrayList<>();
        Cursor cursor = db.query("Province", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Province province = new Province();
                province.setId(cursor.getColumnIndex("id"));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_code")));
                provinceArrayList.add(province);
            } while (cursor.moveToNext());
        }


        return provinceArrayList;
    }

    /**
     * 将城市实例储存到数据库
     */

    public void saveCity(City city) {
        if (city != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("city_name", city.getCityName());
            contentValues.put("city_code", city.getCityCode());
            contentValues.put("province_id", city.getProvinceId());
            db.insert("city", null, contentValues);
        }
    }

    /**
     * 从数据库中读取省份所有的城市信息
     */

    public List<City> loadCity(int provinceId) {
        ArrayList<City> list = new ArrayList<City>();
        Cursor cursor = db.query("City", null, "province_id=?", new String[]
                {String.valueOf(provinceId)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {

                City city = new City();
                city.setId(cursor.getColumnIndex("id"));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(provinceId);
                list.add(city);

            } while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * 将county实例储存到数据库
     */
    public void saveCounty(County county) {

        if (county != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("county_name", county.getCountyName());
            contentValues.put("county_code", county.getCountyCode());
            contentValues.put("city_id", county.getCityId());
            db.insert("city", null, contentValues);
        }

    }

    /**
     * 从数据库中读取某城市下的所有县的信息
     */
    public List<County> loadCounty(int cityId) {

        ArrayList<County> list = new ArrayList<>();
        Cursor cursor = db.query("city", null, "city_id=?", new String[]{String.valueOf(cityId)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                County county = new County();
                county.setId(cursor.getColumnIndex("id"));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCityId(cityId);
                list.add(county);

            } while (cursor.moveToNext());
        }
        return list;
    }


}
