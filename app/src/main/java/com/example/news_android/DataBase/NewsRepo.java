package com.example.news_android.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.news_android.Utils;

import java.util.ArrayList;

public class NewsRepo
{
    private NewsDBHelper dbHelper;
    public NewsRepo(Context context)
    {
        dbHelper=new NewsDBHelper(context);
    }
    public void insert(News news)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(News.categoryKey,news.category);
        values.put(News.contentKey,news.content);
        values.put(News.dateKey,news.date);
        values.put(News.entitiesKey, Utils.convertArrayToString(news.entities));
        values.put(News._idKey,news._id);
        values.put(News.langKey,news.lang);
        values.put(News.sourceKey,news.source);
        values.put(News.timeKey,news.time);
        values.put(News.titleKey,news.title);
        values.put(News.typeKey,news.type);
        values.put(News.relatedEventsKey,Utils.convertArrayToString(news.relatedEvents));

        db.insert(News.TABLE,null,values);
        db.close();
    }

    public void clearTable()
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        dbHelper.onUpgrade(db,3,4);
        db.close();
    }

    public void delete(String _id)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete(News.TABLE,News._idKey+"=?",new String[]{_id});
        db.close();
    }
    public void update(News news)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(News.categoryKey,news.category);
        values.put(News.contentKey,news.content);
        values.put(News.dateKey,news.date);
        values.put(News.entitiesKey,Utils.convertArrayToString(news.entities));
        values.put(News._idKey,news._id);
        values.put(News.langKey,news.lang);
        values.put(News.sourceKey,news.source);
        values.put(News.timeKey,news.time);
        values.put(News.titleKey,news.title);
        values.put(News.typeKey,news.type);
        values.put(News.relatedEventsKey,Utils.convertArrayToString(news.relatedEvents));
        db.update(News.TABLE,values,News._idKey+"=?",new String[]{news._id});
        db.close();
    }

    public ArrayList<News>getNewsList()
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String selectQuery="SELECT "+
                News._idKey+","+
                News.categoryKey+","+
                News.contentKey+","+
                News.dateKey+","+
                News.entitiesKey+","+
                News.langKey+","+
                News.sourceKey+","+
                News.timeKey+","+
                News.titleKey+","+
                News.typeKey+","+
                News.relatedEventsKey+" FROM "+News.TABLE;
        ArrayList<News>newsList=new ArrayList<News>();
        Cursor cursor=db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst())
        {
            do{
                News news=new News();
                news.set_id(cursor.getString(cursor.getColumnIndex(News._idKey)));
                news.setCategory(cursor.getString(cursor.getColumnIndex(News.categoryKey)));
                news.setContent(cursor.getString(cursor.getColumnIndex(News.contentKey)));
                news.setDate(cursor.getString(cursor.getColumnIndex(News.dateKey)));
                news.setEntities(Utils.convertStringToArray(cursor.getString(cursor.getColumnIndex(News.entitiesKey))));
                news.setLang(cursor.getString(cursor.getColumnIndex(News.langKey)));
                news.setSource(cursor.getString(cursor.getColumnIndex(News.sourceKey)));
                news.setTime(cursor.getString(cursor.getColumnIndex(News.timeKey)));
                news.setTitle(cursor.getString(cursor.getColumnIndex(News.titleKey)));
                news.setType(cursor.getString(cursor.getColumnIndex(News.typeKey)));
                news.setRelatedEvents(Utils.convertStringToArray(cursor.getString(cursor.getColumnIndex(News.relatedEventsKey))));
                newsList.add(news);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return newsList;
    }

    public News getNewsById(String id)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery = "SELECT " +
                News._idKey + "," +
                News.categoryKey + "," +
                News.contentKey + "," +
                News.dateKey + "," +
                News.entitiesKey + "," +
                News.langKey + "," +
                News.sourceKey + "," +
                News.timeKey + "," +
                News.titleKey + "," +
                News.typeKey + "," +
                News.relatedEventsKey + " FROM " + News.TABLE +
                " WHERE " +
                News._idKey + "=?";
        News news = new News();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{ id });
        if (cursor.moveToFirst())
        {
            do
            {
                news.set_id(cursor.getString(cursor.getColumnIndex(News._idKey)));
                news.setCategory(cursor.getString(cursor.getColumnIndex(News.categoryKey)));
                news.setContent(cursor.getString(cursor.getColumnIndex(News.contentKey)));
                news.setDate(cursor.getString(cursor.getColumnIndex(News.dateKey)));
                news.setEntities(Utils.convertStringToArray(cursor.getString(cursor.getColumnIndex(News.dateKey))));
                news.setLang(cursor.getString(cursor.getColumnIndex(News.langKey)));
                news.setSource(cursor.getString(cursor.getColumnIndex(News.sourceKey)));
                news.setTime(cursor.getString(cursor.getColumnIndex(News.timeKey)));
                news.setTitle(cursor.getString(cursor.getColumnIndex(News.titleKey)));
                news.setType(cursor.getString(cursor.getColumnIndex(News.typeKey)));
                news.setRelatedEvents(Utils.convertStringToArray(cursor.getString(cursor.getColumnIndex(News.relatedEventsKey))));
            } while (cursor.moveToNext());
        }
        else
        {
            cursor.close();
            db.close();
            return null;
        }
        cursor.close();
        db.close();
        return news;
    }

    public ArrayList<News>getNewsBySearchInput(String searchInput)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery = "SELECT " +
                News._idKey + "," +
                News.categoryKey + "," +
                News.contentKey + "," +
                News.dateKey + "," +
                News.entitiesKey + "," +
                News.langKey + "," +
                News.sourceKey + "," +
                News.timeKey + "," +
                News.titleKey + "," +
                News.typeKey + "," +
                News.relatedEventsKey + " FROM " + News.TABLE +
                " WHERE " +News.titleKey+" LIKE "+"'%"+searchInput+"%'";
        ArrayList<News>res=new ArrayList<News>();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{});
        if (cursor.moveToFirst())
        {
            do
            {
                News news=new News();
                news.set_id(cursor.getString(cursor.getColumnIndex(News._idKey)));
                news.setCategory(cursor.getString(cursor.getColumnIndex(News.categoryKey)));
                news.setContent(cursor.getString(cursor.getColumnIndex(News.contentKey)));
                news.setDate(cursor.getString(cursor.getColumnIndex(News.dateKey)));
                news.setEntities(Utils.convertStringToArray(cursor.getString(cursor.getColumnIndex(News.dateKey))));
                news.setLang(cursor.getString(cursor.getColumnIndex(News.langKey)));
                news.setSource(cursor.getString(cursor.getColumnIndex(News.sourceKey)));
                news.setTime(cursor.getString(cursor.getColumnIndex(News.timeKey)));
                news.setTitle(cursor.getString(cursor.getColumnIndex(News.titleKey)));
                news.setType(cursor.getString(cursor.getColumnIndex(News.typeKey)));
                news.setRelatedEvents(Utils.convertStringToArray(cursor.getString(cursor.getColumnIndex(News.relatedEventsKey))));
                res.add(news);
            } while (cursor.moveToNext());
        }
        return res;
    }

}
