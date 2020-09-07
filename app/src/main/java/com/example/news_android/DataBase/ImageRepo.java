package com.example.news_android.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.example.news_android.Utils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ImageRepo
{
    public static final int MAX_WIDTH=600;
    public static final int MAX_HEIGHT=600;

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

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        int h=img.getHeight();
        int w=img.getWidth();
        double hwRate=(double)h/(double)w;

        if(h>MAX_HEIGHT||w>MAX_WIDTH)
        {
            if(h>MAX_WIDTH)
            {
                double scale=(double)MAX_HEIGHT/(double)h;
                h=MAX_HEIGHT;
                w= (int) (scale*w);
            }
            if(w>MAX_WIDTH)
            {
                double scale=(double)MAX_WIDTH/(double)w;
                w=MAX_WIDTH;
                h= (int) (scale*h);
            }
            img = Bitmap.createScaledBitmap(img,w,h,true);
        }

        values.put(Image.imgURLKey, url);
        values.put(Image.imgContentKey, Utils.getBytesFromBitmap(img));
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
        SQLiteDatabase db = dbHelper.getReadableDatabase();
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
            cursor.close();
            return null;
        }
        db.close();
        return res;
    }
}
