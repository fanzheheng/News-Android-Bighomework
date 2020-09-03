package com.example.news_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import java.util.ArrayList;

public class ImageRepo
{
    private ImageDBHelper dbHelper;

    public ImageRepo(Context context)
    {
        dbHelper = new ImageDBHelper(context);
    }

    public long getCount()
    {
        return dbHelper.getProfilesCount();
    }

    public void insert(String url, Bitmap img)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Image.imgURLKey, url);
        values.put(Image.imgContentKey, Utils.getBytesFromBitmap(img));
        if(getImageByURL(url)==null)
            db.insert(Image.TABLE, null, values);
        db.close();
    }

    public void clearTable()
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbHelper.onUpgrade(db, 3, 4);
        db.close();
    }

    public void delete(String url)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Image.TABLE, Image.imgURLKey + "=?", new String[]{url});
        db.close();
    }

    public void update(String url, Bitmap img)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Image.imgURLKey, url);
        values.put(Image.imgContentKey, Utils.getBytesFromBitmap(img));

        db.update(Image.TABLE, values, Image.imgURLKey + "=?", new String[]{url});
        db.close();
    }

    public ArrayList<String>getImageURLList()
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String selectQuery="SELECT "+
                Image.imgURLKey+ " FROM "+Image.TABLE;

        ArrayList<String>urlList=new ArrayList<String>();
        Cursor cursor=db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst())
        {
            do{
                urlList.add(cursor.getString(cursor.getColumnIndex(Image.imgURLKey)));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return urlList;
    }


    public Bitmap getImageByURL(String url)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery = "SELECT " +
                Image.imgURLKey + "," +
                Image.imgContentKey + " FROM " + Image.TABLE +
                " WHERE " +
                Image.imgURLKey + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{url});
        Bitmap res;
        if (cursor.moveToFirst())
        {
            do
            {
                res=Utils.getImageFromBytes(cursor.getBlob(cursor.getColumnIndex(Image.imgContentKey)));
            } while (cursor.moveToNext());
        } else
        {
            return null;
        }
        cursor.close();
        db.close();
        return res;
    }
}
