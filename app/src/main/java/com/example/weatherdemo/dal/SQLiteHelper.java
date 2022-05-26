package com.example.weatherdemo.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;


import com.example.weatherdemo.model.City;
import com.example.weatherdemo.model.Item;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "weather.db";
    private static int DATABASE_VERSION = 1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("database","Create database");
        String sql = "CREATE TABLE countries("+"id INTEGER PRIMARY KEY AUTOINCREMENT," + "name TEXT, country TEXT, lat DOUBLE, lon DOUBLE)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    // get all
    public List<City> getAll(){
        List<City> list = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("countries", null, null, null, null, null, null);

        while (rs != null && rs.moveToNext()){
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String country = rs.getString(2);
            double lat = rs.getDouble(3);
            double lon = rs.getDouble(4);
            list.add(new City(id, name, country, lat, lon));
        }
        return list;
    }

    // get all
    public List<City> getCityByName(String cityName){
        List<City> list = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("countries", null, null, null, null, null, null);

        while (rs != null && rs.moveToNext()){
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String country = rs.getString(2);
            double lat = rs.getDouble(3);
            double lon = rs.getDouble(4);
            list.add(new City(id, name, country, lat, lon));
        }
        return list;
    }

    // add
    public long addItem(City city){
        System.out.println("add city"+ city.getName());
        ContentValues values = new ContentValues();
        values.put("name", city.getName());
        values.put("country", city.getCountry());
        values.put("lat", city.getLat());
        values.put("lon", city.getLon());
        SQLiteDatabase db = getWritableDatabase();
        return db.insert("countries", null, values);
    }

    // update item
    public int update(City city){
        ContentValues values = new ContentValues();
        values.put("name", city.getName());
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id= ?";
        String[] whereArgs = {Integer.toString(city.getId())};
        return db.update("countries", values, whereClause, whereArgs);
    }

    // delete
    public int delete(int id){
        String whereClause = "id= ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("countries", whereClause, whereArgs);
    }

    // get item by title
    public List<City> searchByName(String key){
        List<City> list = new ArrayList<>();
        String whereClause = "name like ?";
        String[] whereArgs = {"%"+key+"%"};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("countries", null, whereClause, whereArgs, null, null, null);
        while (rs != null && rs.moveToNext()){
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String country = rs.getString(2);
            double lat = rs.getDouble(3);
            double lon = rs.getDouble(4);
            list.add(new City(id, name, country, lat, lon));
        }
        return list;
    }

}
