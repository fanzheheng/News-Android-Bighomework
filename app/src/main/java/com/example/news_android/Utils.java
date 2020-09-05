package com.example.news_android;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Utils
{
    public static String epidemicURL = "https://covid-dashboard.aminer.cn/api/dist/epidemic.json";

    public static String newsEventURL = "https://covid-dashboard.aminer.cn/api/events/list";

    public static String newsContentURL = "https://covid-dashboard.aminer.cn/api/event/5f05f3f69fced0a24b2f84ee";//this one is just for test
    public static String entityURL = "https://innovaapi.aminer.cn/covid/api/v1/pneumonia/entityquery";
    public static String expertURL = "https://innovaapi.aminer.cn/predictor/api/v1/valhalla/highlight/get_ncov_expers_list?v=2";

    public static String strSeparator = "__,__";


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    public static void verifyStoragePermissions(Activity activity)
    {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED)
        {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }


    public static String convertHashMapToString(HashMap<String, String> map)
    {
        String str = "";
        if (map == null) return str;

        Set<String> keys = map.keySet();
        for (String k : keys) {
            str += k;
            str += strSeparator;
            str += map.get(k);
            str += strSeparator;
        }

        return str;
    }

    public static HashMap<String, String> convertStringToHashMap(String str)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        if (str == null) return map;
        String[] arr = str.split(strSeparator);
        for (int i = 0; i < arr.length; i += 2)
        {
            String key = arr[i];
            if (i + 1 < arr.length)
            {
                String value = arr[i + 1];
                map.put(key, value);
            }
        }
        return map;
    }

    public static String convertArrayToString(String[] array)
    {
        String str = "";
        for (int i = 0; i < array.length; i++)
        {
            str = str + array[i];
            // Do not append comma at the end of last element
            if (i < array.length - 1)
            {
                str = str + strSeparator;
            }
        }
        return str;
    }


    public static String convertArrayToString(ArrayList<String> array)
    {
        String str = "";
        if (array == null) return str;
        for (int i = 0; i < array.size(); i++)
        {
            str = str + array.get(i);
            // Do not append comma at the end of last element
            if (i < array.size() - 1)
            {
                str = str + strSeparator;
            }
        }
        return str;
    }

    public static String convertIntegerArrayToString(ArrayList<Integer> array)
    {
        String str = "";
        if (array == null) return str;
        for (int i = 0; i < array.size(); i++)
        {
            str = str + array.get(i).toString();
            // Do not append comma at the end of last element
            if (i < array.size() - 1)
            {
                str = str + strSeparator;
            }
        }
        return str;
    }

    public static ArrayList<Integer> convertStringToIntegerArray(String str)
    {
        ArrayList<Integer> res = new ArrayList<Integer>();
        if (str == null) return res;
        String[] arr = str.split(strSeparator);

        for (int i = 0; i < arr.length; i++)
        {
            res.add(Integer.parseInt(arr[i]));
        }
        return res;
    }

    public static ArrayList<String> convertStringToArray(String str)
    {
        ArrayList<String> res = new ArrayList<String>();
        if (str == null) return res;
        String[] arr = str.split(strSeparator);

        for (int i = 0; i < arr.length; i++)
        {
            res.add(arr[i]);
        }
        return res;
    }

    public static byte[] getBytesFromBitmap(Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
    // convert from byte array to bitmap
    public static Bitmap getImageFromBytes(byte[] image)
    {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static void UpdateNewsDatabase(Context context,JsonGetter.JsonGetterFinishListener listener,boolean readNewest)
    {
        NewsEventJsonGetter jsonGetter=new NewsEventJsonGetter(newsEventURL,context,listener);
        int tmpPage=NewsEventJsonGetter.page;
        if(readNewest)
        {
            NewsEventJsonGetter.updatePage(1);
        }
        jsonGetter.execute();
        if(readNewest)
        {
            NewsEventJsonGetter.updatePage(tmpPage);
        }
    }
    public static void UpdateEntityDatabase(Context context,String label, JsonGetter.JsonGetterFinishListener listener)
    {
        EntityJsonGetter jsonGetter=new EntityJsonGetter(entityURL+"?entity="+label,context,listener);
        jsonGetter.execute();
    }

    public static JsonGetter UpdateExpertDatabase(Context context,JsonGetter.JsonGetterFinishListener listener)
    {
        ExpertJsonGetter jsonGetter=new ExpertJsonGetter(expertURL,context,listener);
        jsonGetter.execute();
        return jsonGetter;
    }
    public static void UpdateEpidemicDatabase(Context context,JsonGetter.JsonGetterFinishListener listener)
    {
        EpidemicDataJsonGetter jsonGetter=new EpidemicDataJsonGetter(epidemicURL,context,listener);
        jsonGetter.execute();
    }

}
