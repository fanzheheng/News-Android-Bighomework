package com.example.news_android.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ExpertRepo
{
    private ExpertDBHelper dbHelper;
    public ExpertRepo(Context context)
    {
        dbHelper=new ExpertDBHelper(context);
    }
    public void insert(Expert expert)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Expert.idKey,expert.id);
        values.put(Expert.avatarKey,expert.avatar);
        values.put(Expert.activityKey,expert.activity);
        values.put(Expert.citationsKey,expert.citations);
        values.put(Expert.diversityKey,expert.diversity);
        values.put(Expert.gindexKey,expert.gindex);
        values.put(Expert.hindexKey,expert.hindex);
        values.put(Expert.newStarKey,expert.newStar);
        values.put(Expert.risingStarKey,expert.risingStar);
        values.put(Expert.sociabilityKey,expert.sociability);
        values.put(Expert.isPassedAwayKey,expert.isPassedAway?1:0);
        values.put(Expert.nameKey,expert.name);
        values.put(Expert.nameZhKey,expert.nameZh);
        values.put(Expert.affiliationKey,expert.affiliation);
        values.put(Expert.affiliationZhKey,expert.affiliationZh);
        values.put(Expert.bioKey,expert.bio);
        values.put(Expert.eduKey,expert.edu);
        values.put(Expert.positionKey,expert.position);
        values.put(Expert.workKey,expert.work);
        db.insert(Expert.TABLE,null,values);
        update(expert);
        db.close();
    }

    public void clearTable()
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        dbHelper.onUpgrade(db,3,4);
        db.close();
    }

    public void delete(String id)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete(Expert.TABLE,Expert.idKey+"=?",new String[]{id});
        db.close();
    }
    public void update(Expert expert)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Expert.idKey,expert.id);
        values.put(Expert.avatarKey,expert.avatar);
        values.put(Expert.activityKey,expert.activity);
        values.put(Expert.citationsKey,expert.citations);
        values.put(Expert.diversityKey,expert.diversity);
        values.put(Expert.gindexKey,expert.gindex);
        values.put(Expert.hindexKey,expert.hindex);
        values.put(Expert.newStarKey,expert.newStar);
        values.put(Expert.risingStarKey,expert.risingStar);
        values.put(Expert.sociabilityKey,expert.sociability);
        values.put(Expert.isPassedAwayKey,expert.isPassedAway?1:0);
        values.put(Expert.nameKey,expert.name);
        values.put(Expert.nameZhKey,expert.nameZh);
        values.put(Expert.affiliationKey,expert.affiliation);
        values.put(Expert.affiliationZhKey,expert.affiliationZh);
        values.put(Expert.bioKey,expert.bio);
        values.put(Expert.eduKey,expert.edu);
        values.put(Expert.positionKey,expert.position);
        values.put(Expert.workKey,expert.work);

        db.update(Expert.TABLE,values,Expert.idKey+"=?",new String[]{expert.id});
        db.close();
    }

    public ArrayList<Expert>getExpertList()
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String selectQuery="SELECT "+
                Expert.idKey+","+
                Expert.avatarKey+","+
                Expert.activityKey+","+
                Expert.citationsKey+","+
                Expert.diversityKey+","+
                Expert.gindexKey+","+
                Expert.hindexKey+","+
                Expert.newStarKey+","+
                Expert.risingStarKey+","+
                Expert.sociabilityKey+","+
                Expert.isPassedAwayKey+","+
                Expert.nameKey+","+
                Expert.nameZhKey+","+
                Expert.affiliationKey+","+
                Expert.affiliationZhKey+","+
                Expert.bioKey+","+
                Expert.eduKey+","+
                Expert.positionKey+","+
                Expert.workKey+" FROM "+Expert.TABLE;
        ArrayList<Expert>expertList=new ArrayList<Expert>();
        Cursor cursor=db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst())
        {
            do{
                Expert expert=new Expert();
                expert.setId(cursor.getString(cursor.getColumnIndex(Expert.idKey)));
                expert.setAvatar(cursor.getString(cursor.getColumnIndex(Expert.avatarKey)));
                expert.setActivity(cursor.getFloat(cursor.getColumnIndex(Expert.activityKey)));
                expert.setCitations(cursor.getInt(cursor.getColumnIndex(Expert.citationsKey)));
                expert.setDiversity(cursor.getFloat(cursor.getColumnIndex(Expert.diversityKey)));
                expert.setGindex(cursor.getFloat(cursor.getColumnIndex(Expert.gindexKey)));
                expert.setHindex(cursor.getFloat(cursor.getColumnIndex(Expert.hindexKey)));
                expert.setNewStar(cursor.getFloat(cursor.getColumnIndex(Expert.newStarKey)));
                expert.setRisingStar(cursor.getFloat(cursor.getColumnIndex(Expert.risingStarKey)));
                expert.setSociability(cursor.getFloat(cursor.getColumnIndex(Expert.sociabilityKey)));
                expert.setPassedAway(cursor.getInt(cursor.getColumnIndex(Expert.isPassedAwayKey))>0);
                expert.setName(cursor.getString(cursor.getColumnIndex(Expert.nameKey)));
                expert.setNameZh(cursor.getString(cursor.getColumnIndex(Expert.nameZhKey)));
                expert.setAffiliation(cursor.getString(cursor.getColumnIndex(Expert.affiliationKey)));
                expert.setAffiliationZh(cursor.getString(cursor.getColumnIndex(Expert.affiliationZhKey)));
                expert.setBio(cursor.getString(cursor.getColumnIndex(Expert.bioKey)));
                expert.setEdu(cursor.getString(cursor.getColumnIndex(Expert.eduKey)));
                expert.setPosition(cursor.getString(cursor.getColumnIndex(Expert.positionKey)));
                expert.setWork(cursor.getString(cursor.getColumnIndex(Expert.workKey)));

                expertList.add(expert);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return expertList;
    }

    public Expert getExpertById(String id)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String selectQuery="SELECT "+
                Expert.idKey+","+
                Expert.avatarKey+","+
                Expert.activityKey+","+
                Expert.citationsKey+","+
                Expert.diversityKey+","+
                Expert.gindexKey+","+
                Expert.hindexKey+","+
                Expert.newStarKey+","+
                Expert.risingStarKey+","+
                Expert.sociabilityKey+","+
                Expert.isPassedAwayKey+","+
                Expert.nameKey+","+
                Expert.nameZhKey+","+
                Expert.affiliationKey+","+
                Expert.affiliationZhKey+","+
                Expert.bioKey+","+
                Expert.eduKey+","+
                Expert.positionKey+","+
                Expert.workKey+" FROM "+Expert.TABLE+
                " WHERE " +
                Expert.idKey + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{ id });
        Expert expert=new Expert();
        if (cursor.moveToFirst())
        {
            do
            {
                expert.setId(cursor.getString(cursor.getColumnIndex(Expert.idKey)));
                expert.setAvatar(cursor.getString(cursor.getColumnIndex(Expert.avatarKey)));
                expert.setActivity(cursor.getFloat(cursor.getColumnIndex(Expert.activityKey)));
                expert.setCitations(cursor.getInt(cursor.getColumnIndex(Expert.citationsKey)));
                expert.setDiversity(cursor.getFloat(cursor.getColumnIndex(Expert.diversityKey)));
                expert.setGindex(cursor.getFloat(cursor.getColumnIndex(Expert.gindexKey)));
                expert.setHindex(cursor.getFloat(cursor.getColumnIndex(Expert.hindexKey)));
                expert.setNewStar(cursor.getFloat(cursor.getColumnIndex(Expert.newStarKey)));
                expert.setRisingStar(cursor.getFloat(cursor.getColumnIndex(Expert.risingStarKey)));
                expert.setSociability(cursor.getFloat(cursor.getColumnIndex(Expert.sociabilityKey)));
                expert.setPassedAway(cursor.getInt(cursor.getColumnIndex(Expert.isPassedAwayKey))>0);
                expert.setName(cursor.getString(cursor.getColumnIndex(Expert.nameKey)));
                expert.setNameZh(cursor.getString(cursor.getColumnIndex(Expert.nameZhKey)));
                expert.setAffiliation(cursor.getString(cursor.getColumnIndex(Expert.affiliationKey)));
                expert.setAffiliationZh(cursor.getString(cursor.getColumnIndex(Expert.affiliationZhKey)));
                expert.setBio(cursor.getString(cursor.getColumnIndex(Expert.bioKey)));
                expert.setEdu(cursor.getString(cursor.getColumnIndex(Expert.eduKey)));
                expert.setPosition(cursor.getString(cursor.getColumnIndex(Expert.positionKey)));
                expert.setWork(cursor.getString(cursor.getColumnIndex(Expert.workKey)));

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
        return expert;
    }

    public ArrayList<String>getAllExpertId()
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String selectQuery="SELECT "+
                Expert.idKey+" FROM "+Expert.TABLE;
        Cursor cursor = db.rawQuery(selectQuery, new String[]{});
        ArrayList<String>res=new ArrayList<String>();
        if(cursor.moveToFirst())
        {
            do
            {
                res.add(cursor.getString(cursor.getColumnIndex(Expert.idKey)));
            }while(cursor.moveToNext());
        }
        return res;
    }

    public ArrayList<String>getAliveExpertId()
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String selectQuery="SELECT "+
                Expert.idKey+" FROM "+Expert.TABLE
                +" WHERE "+Expert.isPassedAwayKey+"=?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{"0"});
        ArrayList<String>res=new ArrayList<String>();
        if(cursor.moveToFirst())
        {
            do
            {
                res.add(cursor.getString(cursor.getColumnIndex(Expert.idKey)));
            }while(cursor.moveToNext());
        }
        return res;
    }
    public ArrayList<String>getDeadExpertId()
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String selectQuery="SELECT "+
                Expert.idKey+" FROM "+Expert.TABLE
                +" WHERE "+Expert.isPassedAwayKey+"=?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{"1"});
        ArrayList<String>res=new ArrayList<String>();
        if(cursor.moveToFirst())
        {
            do
            {
                res.add(cursor.getString(cursor.getColumnIndex(Expert.idKey)));
            }while(cursor.moveToNext());
        }
        return res;
    }

}
