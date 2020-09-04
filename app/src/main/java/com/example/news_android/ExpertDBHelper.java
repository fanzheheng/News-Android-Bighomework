package com.example.news_android;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class ExpertDBHelper extends SQLiteOpenHelper
{

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "expert.db";


    public ExpertDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    public ExpertDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler)
    {
        super(context, name, factory, version, errorHandler);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public ExpertDBHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams)
    {
        super(context, name, version, openParams);
    }

    public ExpertDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String CREATE_TABLE = "CREATE TABLE " + Expert.TABLE + "("
                + Expert.idKey + " TEXT PRIMARY KEY,"
                + Expert.avatarKey + " TEXT,"
                + Expert.activityKey + " REAL,"
                + Expert.citationsKey + " INTEGER,"
                + Expert.diversityKey + " REAL,"
                + Expert.gindexKey + " REAL,"
                + Expert.hindexKey + " REAL,"
                + Expert.newStarKey + " REAL,"
                + Expert.risingStarKey + " REAL,"
                + Expert.sociabilityKey + " REAL,"
                + Expert.isPassedAwayKey + " INTEGER,"
                + Expert.nameKey + " TEXT,"
                + Expert.nameZhKey + " TEXT,"
                + Expert.affiliationKey + " TEXT,"
                + Expert.affiliationZhKey + " TEXT,"
                + Expert.bioKey + " TEXT,"
                + Expert.eduKey + " TEXT,"
                + Expert.positionKey + " TEXT,"
                + Expert.workKey + " TEXT)";

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Expert.TABLE);
        onCreate(sqLiteDatabase);
    }
}
