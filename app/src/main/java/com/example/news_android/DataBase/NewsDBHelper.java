package com.example.news_android.DataBase;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class NewsDBHelper extends SQLiteOpenHelper
{

    private static final int DATABASE_VERSION=3;
    private static final String DATABASE_NAME="news.db";


    public NewsDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    public NewsDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler)
    {
        super(context, name, factory, version, errorHandler);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public NewsDBHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams)
    {
        super(context, name, version, openParams);
    }

    public NewsDBHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String CREATE_TABLE="CREATE TABLE "+ News.TABLE+"("
                         +News._idKey+" TEXT PRIMARY KEY,"
                         +News.typeKey+" TEXT,"
                         +News.titleKey+" TEXT,"
                         +News.categoryKey+" TEXT,"
                         +News.timeKey+" TEXT,"
                         +News.langKey+" TEXT,"
                         +News.contentKey+" TEXT,"
                         +News.dateKey+" TEXT,"
                         +News.sourceKey+" TEXT,"
                         +News.entitiesKey+" TEXT,"
                         +News.relatedEventsKey+" TEXT,"
                         +News.clusterKey+" INT)";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ News.TABLE);
        onCreate(sqLiteDatabase);
    }
}
