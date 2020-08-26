package com.example.news_android;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;

public class JsonGetter extends AsyncTask
{
    String url;

    public JsonGetter(String url)
    {
        this.url = url;
    }

    @Override
    protected void onPostExecute(Object o)
    {
        super.onPostExecute(o);
    }

    @Override
    protected JSONObject doInBackground(Object[] objects)
    {
        JSONObject jsonObject=null;
        URL targetURL = null;
        try
        {
            targetURL = new URL(url);
            URLConnection tc = targetURL.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(tc.getInputStream()));
            StringBuffer buffer = new StringBuffer();
            String tmp = new String();
            while ((tmp = in.readLine()) != null)
            {
                buffer.append(tmp);
            }
            String builder = buffer.toString();
            jsonObject = new JSONObject(builder);

        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        } catch (JSONException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println(jsonObject);
        return jsonObject;
    }

}

class CountryDataJsonGetter extends JsonGetter
{
    public CountryDataJsonGetter(String url)
    {
        super(url);
    }
    static JSONObject countryDataJson=null;
    @Override
    protected void onPostExecute(Object o)
    {
        super.onPostExecute(o);

        countryDataJson= (JSONObject) o;
        if(o==null)return;
        Iterator<String>keys=countryDataJson.keys();
        while(keys.hasNext())
        {
            String keyName=keys.next();
            System.out.println(keyName);
            try
            {
                String content=countryDataJson.getString(keyName);
                System.out.println(content);
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }
}

class NewsEventJsonGetter extends JsonGetter
{
    public NewsEventJsonGetter(String url)
    {
        super(url);
    }
    static JSONObject newsEventJson=null;
    static JSONArray newsEventJsonArray=null;
    @Override
    protected void onPostExecute(Object o)
    {
        super.onPostExecute(o);
        newsEventJson= (JSONObject) o;

        try
        {
            newsEventJsonArray=newsEventJson.getJSONArray("datas");
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        if(newsEventJsonArray!=null)
        {
            for(int i=0;i<newsEventJsonArray.length();i++)
            {
                try
                {
                    System.out.println(newsEventJsonArray.get(i));
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}

class NewsContentJsonGetter extends JsonGetter
{
    public NewsContentJsonGetter(String url)
    {
        super(url);
    }
    static JSONObject newsContentJson=null;

    @Override
    protected void onPostExecute(Object o)
    {
        super.onPostExecute(o);
        JSONObject topObject= (JSONObject) o;
        try
        {
            newsContentJson=topObject.getJSONObject("data");
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        System.out.println(newsContentJson);

    }
}

class EntityJsonGetter extends JsonGetter
{
    public EntityJsonGetter(String url)
    {
        super(url);
    }
    static JSONObject entityJson=null;
    static JSONArray entityJsonArray=null;
    @Override
    protected void onPostExecute(Object o)
    {
        super.onPostExecute(o);
        entityJson= (JSONObject) o;

        try
        {
            entityJsonArray= entityJson.getJSONArray("data");
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        if(entityJsonArray!=null)
        {
            for(int i=0;i<entityJsonArray.length();i++)
            {
                try
                {
                    System.out.println(entityJsonArray.get(i));
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}


class ExpertJsonGetter extends JsonGetter
{
    public ExpertJsonGetter(String url)
    {
        super(url);
    }
    static JSONObject expertJson=null;
    static JSONArray expertJsonArray=null;
    @Override
    protected void onPostExecute(Object o)
    {
        super.onPostExecute(o);
        expertJson= (JSONObject) o;

        try
        {
            expertJsonArray= expertJson.getJSONArray("data");
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        if(expertJsonArray!=null)
        {
            for(int i=0;i<expertJsonArray.length();i++)
            {
                try
                {
                    System.out.println(expertJsonArray.get(i));
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}




