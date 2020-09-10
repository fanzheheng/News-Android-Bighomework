package com.example.news_android.DataBase;

import java.util.ArrayList;

public class News
{

    public static final String TABLE="News";

    public static final String _idKey="_id";
    public static final String typeKey="type";
    public static final String titleKey="title";
    public static final String categoryKey="category";
    public static final String timeKey="time";
    public static final String langKey="lang";


    public static final String contentKey="content";
    public static final String dateKey="date";
    public static final String sourceKey="source";
    public static final String entitiesKey="entities";
    public static final String relatedEventsKey="related_events";
    public static final String clusterKey="cluster";

    public  String _id;
    public  String type;
    public  String title;
    public  String category;
    public  String time;
    public  String lang;

    public String get_id()
    {
        return _id;
    }

    public void set_id(String _id)
    {
        this._id = _id;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getLang()
    {
        return lang;
    }

    public void setLang(String lang)
    {
        this.lang = lang;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }



    public ArrayList<String> getEntities()
    {
        return entities;
    }

    public void setEntities(ArrayList<String> entities)
    {
        this.entities = entities;
    }

    public ArrayList<String> getRelatedEvents()
    {
        return relatedEvents;
    }

    public void setRelatedEvents(ArrayList<String> relatedEvents)
    {
        this.relatedEvents = relatedEvents;
    }

    public void setCluster(int cluster)
    {
        this.cluster = cluster;
    }

    public  String content;
    public  String date="";
    public  String source;
    public int cluster=-1;
    public ArrayList<String> entities=new ArrayList<String>();//label of related entities
    public ArrayList<String> relatedEvents=new ArrayList<String>();//id of related events

}
