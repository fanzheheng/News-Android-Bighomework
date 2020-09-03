package com.example.news_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class EpidemicRepo
{
    private EpidemicDBHelper dbHelper;

    public EpidemicRepo(Context context)
    {
        dbHelper = new EpidemicDBHelper(context);
    }

    public void insert(EpidemicData epidemic)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EpidemicData.beginDateKey, epidemic.beginDate);
        values.put(EpidemicData.confirmedKey, Utils.convertIntegerArrayToString(epidemic.confirmed));
        values.put(EpidemicData.curedKey, Utils.convertIntegerArrayToString(epidemic.cured));
        values.put(EpidemicData.deadKey, Utils.convertIntegerArrayToString(epidemic.dead));
        values.put(EpidemicData.districtKey,epidemic.district);
        values.put(EpidemicData.countryKey, epidemic.country);
        values.put(EpidemicData.provinceKey, epidemic.province);
        values.put(EpidemicData.cityKey, epidemic.city);
        if (getEpidemicByDistrict(epidemic.district) == null)
            db.insert(EpidemicData.TABLE, null, values);
        else
            update(epidemic);
        db.close();
    }

    public void clearTable()
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbHelper.onUpgrade(db, 3, 4);
        db.close();
    }

    public void delete(String district)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(EpidemicData.TABLE, EpidemicData.districtKey + "=?", new String[]{district});
        db.close();
    }

    public void update(EpidemicData epidemic)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EpidemicData.beginDateKey, epidemic.beginDate);
        values.put(EpidemicData.confirmedKey, Utils.convertIntegerArrayToString(epidemic.confirmed));
        values.put(EpidemicData.curedKey, Utils.convertIntegerArrayToString(epidemic.cured));
        values.put(EpidemicData.deadKey, Utils.convertIntegerArrayToString(epidemic.dead));
        values.put(EpidemicData.countryKey, epidemic.country);
        values.put(EpidemicData.provinceKey, epidemic.province);
        values.put(EpidemicData.cityKey, epidemic.city);
        db.update(EpidemicData.TABLE, values, EpidemicData.districtKey + "=?", new String[]{epidemic.district});
        db.close();
    }

    public ArrayList<EpidemicData> getEpidemicList()
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery = "SELECT " +
                EpidemicData.beginDateKey + "," +
                EpidemicData.districtKey + "," +
                EpidemicData.curedKey + "," +
                EpidemicData.confirmedKey + "," +
                EpidemicData.countryKey + "," +
                EpidemicData.cityKey + "," +
                EpidemicData.provinceKey + "," +
                EpidemicData.deadKey + " FROM " + EpidemicData.TABLE;

        ArrayList<EpidemicData> epidemicList = new ArrayList<EpidemicData>();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do
            {
                EpidemicData epidemic = new EpidemicData();
                epidemic.setBeginDate(cursor.getString(cursor.getColumnIndex(EpidemicData.beginDateKey)));
                epidemic.setDistrict(cursor.getString(cursor.getColumnIndex(EpidemicData.districtKey)));
                epidemic.setConfirmed(Utils.convertStringToIntegerArray(cursor.getString(cursor.getColumnIndex(EpidemicData.confirmedKey))));
                epidemic.setCured(Utils.convertStringToIntegerArray(cursor.getString(cursor.getColumnIndex(EpidemicData.curedKey))));
                epidemic.setDead(Utils.convertStringToIntegerArray(cursor.getString(cursor.getColumnIndex(EpidemicData.deadKey))));
                epidemic.setCountry(cursor.getString(cursor.getColumnIndex(EpidemicData.countryKey)));
                epidemic.setProvince(cursor.getString(cursor.getColumnIndex(EpidemicData.provinceKey)));
                epidemic.setCity(cursor.getString(cursor.getColumnIndex(EpidemicData.cityKey)));
                epidemicList.add(epidemic);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return epidemicList;
    }

    public ArrayList<EpidemicData> getEpidemicByCountry(String country)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery = "SELECT " +
                EpidemicData.beginDateKey + "," +
                EpidemicData.districtKey + "," +
                EpidemicData.curedKey + "," +
                EpidemicData.confirmedKey + "," +
                EpidemicData.countryKey + "," +
                EpidemicData.cityKey + "," +
                EpidemicData.provinceKey + "," +
                EpidemicData.deadKey + " FROM " + EpidemicData.TABLE +
                " WHERE " +
                EpidemicData.countryKey + "=?";

        ArrayList<EpidemicData> epidemicList = new ArrayList<EpidemicData>();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{country});
        if (cursor.moveToFirst())
        {
            do
            {
                EpidemicData epidemic = new EpidemicData();
                epidemic.setBeginDate(cursor.getString(cursor.getColumnIndex(EpidemicData.beginDateKey)));
                epidemic.setDistrict(cursor.getString(cursor.getColumnIndex(EpidemicData.districtKey)));
                epidemic.setConfirmed(Utils.convertStringToIntegerArray(cursor.getString(cursor.getColumnIndex(EpidemicData.confirmedKey))));
                epidemic.setCured(Utils.convertStringToIntegerArray(cursor.getString(cursor.getColumnIndex(EpidemicData.curedKey))));
                epidemic.setDead(Utils.convertStringToIntegerArray(cursor.getString(cursor.getColumnIndex(EpidemicData.deadKey))));
                epidemic.setCountry(cursor.getString(cursor.getColumnIndex(EpidemicData.countryKey)));
                epidemic.setProvince(cursor.getString(cursor.getColumnIndex(EpidemicData.provinceKey)));
                epidemic.setCity(cursor.getString(cursor.getColumnIndex(EpidemicData.cityKey)));
                epidemicList.add(epidemic);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return epidemicList;
    }

    public ArrayList<EpidemicData> getEpidemicByProvince(String province)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery = "SELECT " +
                EpidemicData.beginDateKey + "," +
                EpidemicData.districtKey + "," +
                EpidemicData.curedKey + "," +
                EpidemicData.confirmedKey + "," +
                EpidemicData.countryKey + "," +
                EpidemicData.cityKey + "," +
                EpidemicData.provinceKey + "," +
                EpidemicData.deadKey + " FROM " + EpidemicData.TABLE +
                " WHERE " +
                EpidemicData.provinceKey + "=?";

        ArrayList<EpidemicData> epidemicList = new ArrayList<EpidemicData>();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{province});
        if (cursor.moveToFirst())
        {
            do
            {
                EpidemicData epidemic = new EpidemicData();
                epidemic.setBeginDate(cursor.getString(cursor.getColumnIndex(EpidemicData.beginDateKey)));
                epidemic.setDistrict(cursor.getString(cursor.getColumnIndex(EpidemicData.districtKey)));
                epidemic.setConfirmed(Utils.convertStringToIntegerArray(cursor.getString(cursor.getColumnIndex(EpidemicData.confirmedKey))));
                epidemic.setCured(Utils.convertStringToIntegerArray(cursor.getString(cursor.getColumnIndex(EpidemicData.curedKey))));
                epidemic.setDead(Utils.convertStringToIntegerArray(cursor.getString(cursor.getColumnIndex(EpidemicData.deadKey))));
                epidemic.setCountry(cursor.getString(cursor.getColumnIndex(EpidemicData.countryKey)));
                epidemic.setProvince(cursor.getString(cursor.getColumnIndex(EpidemicData.provinceKey)));
                epidemic.setCity(cursor.getString(cursor.getColumnIndex(EpidemicData.cityKey)));
                epidemicList.add(epidemic);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return epidemicList;
    }

    public ArrayList<EpidemicData> getEpidemicByCity(String city)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery = "SELECT " +
                EpidemicData.beginDateKey + "," +
                EpidemicData.districtKey + "," +
                EpidemicData.curedKey + "," +
                EpidemicData.confirmedKey + "," +
                EpidemicData.countryKey + "," +
                EpidemicData.cityKey + "," +
                EpidemicData.provinceKey + "," +
                EpidemicData.deadKey + " FROM " + EpidemicData.TABLE +
                " WHERE " +
                EpidemicData.cityKey + "=?";

        ArrayList<EpidemicData> epidemicList = new ArrayList<EpidemicData>();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{city});
        if (cursor.moveToFirst())
        {
            do
            {
                EpidemicData epidemic = new EpidemicData();
                epidemic.setBeginDate(cursor.getString(cursor.getColumnIndex(EpidemicData.beginDateKey)));
                epidemic.setDistrict(cursor.getString(cursor.getColumnIndex(EpidemicData.districtKey)));
                epidemic.setConfirmed(Utils.convertStringToIntegerArray(cursor.getString(cursor.getColumnIndex(EpidemicData.confirmedKey))));
                epidemic.setCured(Utils.convertStringToIntegerArray(cursor.getString(cursor.getColumnIndex(EpidemicData.curedKey))));
                epidemic.setDead(Utils.convertStringToIntegerArray(cursor.getString(cursor.getColumnIndex(EpidemicData.deadKey))));
                epidemic.setCountry(cursor.getString(cursor.getColumnIndex(EpidemicData.countryKey)));
                epidemic.setProvince(cursor.getString(cursor.getColumnIndex(EpidemicData.provinceKey)));
                epidemic.setCity(cursor.getString(cursor.getColumnIndex(EpidemicData.cityKey)));
                epidemicList.add(epidemic);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return epidemicList;
    }

    public EpidemicData getEpidemicByDistrict(String district)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery = "SELECT " +
                EpidemicData.beginDateKey + "," +
                EpidemicData.districtKey + "," +
                EpidemicData.curedKey + "," +
                EpidemicData.confirmedKey + "," +
                EpidemicData.countryKey + "," +
                EpidemicData.cityKey + "," +
                EpidemicData.provinceKey + "," +
                EpidemicData.deadKey + " FROM " + EpidemicData.TABLE +
                " WHERE " +
                EpidemicData.districtKey + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{district});
        EpidemicData epidemic = new EpidemicData();
        if (cursor.moveToFirst())
        {
            do
            {
                epidemic.setBeginDate(cursor.getString(cursor.getColumnIndex(EpidemicData.beginDateKey)));
                epidemic.setDistrict(cursor.getString(cursor.getColumnIndex(EpidemicData.districtKey)));
                epidemic.setConfirmed(Utils.convertStringToIntegerArray(cursor.getString(cursor.getColumnIndex(EpidemicData.confirmedKey))));
                epidemic.setCured(Utils.convertStringToIntegerArray(cursor.getString(cursor.getColumnIndex(EpidemicData.curedKey))));
                epidemic.setDead(Utils.convertStringToIntegerArray(cursor.getString(cursor.getColumnIndex(EpidemicData.deadKey))));
                epidemic.setCountry(cursor.getString(cursor.getColumnIndex(EpidemicData.countryKey)));
                epidemic.setProvince(cursor.getString(cursor.getColumnIndex(EpidemicData.provinceKey)));
                epidemic.setCity(cursor.getString(cursor.getColumnIndex(EpidemicData.cityKey)));
            } while (cursor.moveToNext());
        } else
        {
            return null;
        }
        cursor.close();
        db.close();
        return epidemic;
    }

}
