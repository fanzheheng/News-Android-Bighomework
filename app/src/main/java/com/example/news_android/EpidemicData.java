package com.example.news_android;

import java.util.ArrayList;

public class EpidemicData
{
    public static final String TABLE="EpidemicData";

    public static final String districtKey="district";
    public static final String countryKey="country";
    public static final String provinceKey="province";
    public static final String cityKey="city";// also refer to county(so that you don't mix with country)

    public static final String beginDateKey="beginDate";
    public static final String jsonBeginDateKey="begin";
    public static final String jsonDataKey="data";
    public static final String confirmedKey="confirmed";
    public static final String curedKey="cured";
    public static final String deadKey="dead";

    public void setBeginDate(String begin)
    {
        this.beginDate = begin;
    }

    public void setDistrict(String district)
    {
        this.district = district;
    }

    public void setConfirmed(ArrayList<Integer> confirmed)
    {
        this.confirmed = confirmed;
    }

    public void setCured(ArrayList<Integer> cured)
    {
        this.cured = cured;
    }

    public void setDead(ArrayList<Integer> dead)
    {
        this.dead = dead;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public void setProvince(String province)
    {
        this.province = province;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String beginDate;
    public String district;
    public String country="";
    public String province="";
    public String city="";
    public ArrayList<Integer>confirmed=new ArrayList<Integer>();
    public ArrayList<Integer>cured=new ArrayList<Integer>();
    public ArrayList<Integer>dead=new ArrayList<Integer>();
}
