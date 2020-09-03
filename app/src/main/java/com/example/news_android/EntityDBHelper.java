package com.example.news_android;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class EntityDBHelper extends SQLiteOpenHelper
{

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "entity.db";


    public EntityDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    public EntityDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler)
    {
        super(context, name, factory, version, errorHandler);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public EntityDBHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams)
    {
        super(context, name, version, openParams);
    }

    public EntityDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String CREATE_TABLE = "CREATE TABLE " + Entity.TABLE + "("
                + Entity.labelKey + " TEXT PRIMARY KEY,"
                + Entity.urlKey + " TEXT,"
                + Entity.enwikiKey + " TEXT,"
                + Entity.baiduKey + " TEXT,"
                + Entity.zhwikiKey + " TEXT,"
                + Entity.propertiesKey + " TEXT,"
                + Entity.parentsKey + " TEXT,"
                + Entity.childrenKey + " TEXT,"
                + Entity.imgURLKey + " TEXT)";

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Entity.TABLE);
        onCreate(sqLiteDatabase);
    }
}
