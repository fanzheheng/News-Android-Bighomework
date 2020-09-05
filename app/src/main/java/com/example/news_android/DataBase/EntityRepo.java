package com.example.news_android.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.news_android.Utils;

import java.util.ArrayList;

public class EntityRepo
{
    private EntityDBHelper dbHelper;
    public EntityRepo(Context context)
    {
        dbHelper=new EntityDBHelper(context);
    }
    public void insert(Entity entity)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Entity.baiduKey,entity.baidu);
        values.put(Entity.childrenKey, Utils.convertArrayToString(entity.children));
        values.put(Entity.enwikiKey,entity.enwiki);
        values.put(Entity.imgURLKey,entity.imgURL);
        values.put(Entity.labelKey,entity.label);
        values.put(Entity.urlKey,entity.url);
        values.put(Entity.parentsKey,Utils.convertArrayToString(entity.parents));
        values.put(Entity.propertiesKey,Utils.convertHashMapToString(entity.properties));
        values.put(Entity.zhwikiKey,entity.zhwiki);
        db.insert(Entity.TABLE,null,values);
        db.close();
    }

    public void clearTable()
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        dbHelper.onUpgrade(db,3,4);
        db.close();
    }

    public void delete(String label)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete(Entity.TABLE,Entity.labelKey+"=?",new String[]{label});
        db.close();
    }
    public void update(Entity entity)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Entity.baiduKey,entity.baidu);
        values.put(Entity.childrenKey,Utils.convertArrayToString(entity.children));
        values.put(Entity.enwikiKey,entity.enwiki);
        values.put(Entity.imgURLKey,entity.imgURL);
        values.put(Entity.labelKey,entity.label);
        values.put(Entity.urlKey,entity.url);
        values.put(Entity.parentsKey,Utils.convertArrayToString(entity.parents));
        values.put(Entity.propertiesKey,Utils.convertHashMapToString(entity.properties));
        values.put(Entity.zhwikiKey,entity.zhwiki);

        db.update(Entity.TABLE,values,Entity.labelKey+"=?",new String[]{entity.label});
        db.close();
    }

    public ArrayList<Entity>getEntityList()
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String selectQuery="SELECT "+
                Entity.baiduKey+","+
                Entity.childrenKey+","+
                Entity.imgURLKey+","+
                Entity.labelKey+","+
                Entity.parentsKey+","+
                Entity.enwikiKey+","+
                Entity.zhwikiKey+","+
                Entity.urlKey+","+
                Entity.propertiesKey+" FROM "+Entity.TABLE;
        ArrayList<Entity>entityList=new ArrayList<Entity>();
        Cursor cursor=db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst())
        {
            do{
                Entity entity=new Entity();
                entity.setBaidu(cursor.getString(cursor.getColumnIndex(Entity.baiduKey)));
                entity.setChildren(Utils.convertStringToArray(cursor.getString(cursor.getColumnIndex(Entity.childrenKey))));
                entity.setEnwiki(cursor.getString(cursor.getColumnIndex(Entity.enwikiKey)));
                entity.setImgURL(cursor.getString(cursor.getColumnIndex(Entity.imgURLKey)));
                entity.setLabel(cursor.getString(cursor.getColumnIndex(Entity.labelKey)));
                entity.setParents(Utils.convertStringToArray(cursor.getString(cursor.getColumnIndex(Entity.parentsKey))));
                entity.setProperties(Utils.convertStringToHashMap(cursor.getString(cursor.getColumnIndex(Entity.propertiesKey))));
                entity.setUrl(cursor.getString(cursor.getColumnIndex(Entity.urlKey)));
                entity.setZhwiki(cursor.getString(cursor.getColumnIndex(Entity.zhwikiKey)));
                entityList.add(entity);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return entityList;
    }

    public Entity getEntityByLabel(String label)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String selectQuery="SELECT "+
                Entity.baiduKey+","+
                Entity.childrenKey+","+
                Entity.imgURLKey+","+
                Entity.labelKey+","+
                Entity.parentsKey+","+
                Entity.enwikiKey+","+
                Entity.zhwikiKey+","+
                Entity.urlKey+","+
                Entity.propertiesKey+" FROM "+Entity.TABLE+
                " WHERE " +
                Entity.labelKey + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{ label });
        Entity entity=new Entity();
        if (cursor.moveToFirst())
        {
            do
            {
                entity.setBaidu(cursor.getString(cursor.getColumnIndex(Entity.baiduKey)));
                entity.setChildren(Utils.convertStringToArray(cursor.getString(cursor.getColumnIndex(Entity.childrenKey))));
                entity.setEnwiki(cursor.getString(cursor.getColumnIndex(Entity.enwikiKey)));
                entity.setImgURL(cursor.getString(cursor.getColumnIndex(Entity.imgURLKey)));
                entity.setLabel(cursor.getString(cursor.getColumnIndex(Entity.labelKey)));
                entity.setParents(Utils.convertStringToArray(cursor.getString(cursor.getColumnIndex(Entity.parentsKey))));
                entity.setProperties(Utils.convertStringToHashMap(cursor.getString(cursor.getColumnIndex(Entity.propertiesKey))));
                entity.setUrl(cursor.getString(cursor.getColumnIndex(Entity.urlKey)));
                entity.setZhwiki(cursor.getString(cursor.getColumnIndex(Entity.zhwikiKey)));
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
        return entity;
    }

    public ArrayList<Entity>getEntityBySearchInput(String searchInput)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String selectQuery="SELECT "+
                Entity.baiduKey+","+
                Entity.childrenKey+","+
                Entity.imgURLKey+","+
                Entity.labelKey+","+
                Entity.parentsKey+","+
                Entity.enwikiKey+","+
                Entity.zhwikiKey+","+
                Entity.urlKey+","+
                Entity.propertiesKey+" FROM "+Entity.TABLE+
                " WHERE " +
                Entity.labelKey + " LIKE "+"'%"+searchInput+"%'";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{});
        ArrayList<Entity>res=new ArrayList<Entity>();
        if (cursor.moveToFirst())
        {
            do
            {
                Entity entity=new Entity();
                entity.setBaidu(cursor.getString(cursor.getColumnIndex(Entity.baiduKey)));
                entity.setChildren(Utils.convertStringToArray(cursor.getString(cursor.getColumnIndex(Entity.childrenKey))));
                entity.setEnwiki(cursor.getString(cursor.getColumnIndex(Entity.enwikiKey)));
                entity.setImgURL(cursor.getString(cursor.getColumnIndex(Entity.imgURLKey)));
                entity.setLabel(cursor.getString(cursor.getColumnIndex(Entity.labelKey)));
                entity.setParents(Utils.convertStringToArray(cursor.getString(cursor.getColumnIndex(Entity.parentsKey))));
                entity.setProperties(Utils.convertStringToHashMap(cursor.getString(cursor.getColumnIndex(Entity.propertiesKey))));
                entity.setUrl(cursor.getString(cursor.getColumnIndex(Entity.urlKey)));
                entity.setZhwiki(cursor.getString(cursor.getColumnIndex(Entity.zhwikiKey)));
                res.add(entity);
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
        return res;
    }
}
