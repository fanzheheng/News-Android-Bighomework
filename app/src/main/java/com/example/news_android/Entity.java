package com.example.news_android;

import java.util.ArrayList;
import java.util.HashMap;

public class Entity
{
    public static final String TABLE="Entities";

    public static final String labelKey="label";
    public static final String urlKey="url";
    public static final String abstractInfoKey="abstractInfo";
    public static final String enwikiKey="enwiki";
    public static final String baiduKey="baidu";
    public static final String zhwikiKey="zhwiki";
    public static final String COVIDKey="COVID";
    public static final String propertiesKey="properties";
    public static final String relationsKey="relations";
    public static final String parentsKey="parents";
    public static final String childrenKey="parents";
    public static final String imgKey="img";
    public static final String forwardKey="forward";

    public void setLabel(String label)
    {
        this.label = label;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public void setEnwiki(String enwiki)
    {
        this.enwiki = enwiki;
    }

    public void setBaidu(String baidu)
    {
        this.baidu = baidu;
    }

    public void setZhwiki(String zhwiki)
    {
        this.zhwiki = zhwiki;
    }

    public void setImg(String img)
    {
        this.img = img;
    }

    public void setProperties(HashMap<String, String> properties)
    {
        this.properties = properties;
    }

    public void setParents(ArrayList<String> parents)
    {
        this.parents = parents;
    }

    public void setChildren(ArrayList<String> children)
    {
        this.children = children;
    }

    public String enwiki;
    public String baidu;
    public String zhwiki;
    public HashMap<String,String>properties;
    public ArrayList<String> parents=new ArrayList<String>();   //"relation"+"__,__"+"url"+"__,__"+"label"
    public ArrayList<String> children=new ArrayList<String>();  //
    public String img;//image file name (identical to image url)
    public String label;
    public String url;

}
