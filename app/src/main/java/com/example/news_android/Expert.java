package com.example.news_android;

public class Expert
{
    public static final String TABLE="expert";

    public static final String idKey="id";
    public static final String avatarKey="avatar";
    public static final String jsonIndicesKey="indices";
    public static final String jsonProfileKey="profile";
    public static final String activityKey="activity";
    public static final String citationsKey="citations";
    public static final String diversityKey="diversity";
    public static final String gindexKey="gindex";
    public static final String hindexKey="hindex";
    public static final String newStarKey="newStar";
    public static final String risingStarKey="risingStar";
    public static final String sociabilityKey="sociability";
    public static final String isPassedAwayKey="is_passedaway";
    public static final String nameKey="name";
    public static final String nameZhKey="name_zh";
    public static final String affiliationKey="affiliation";
    public static final String affiliationZhKey="affiliation_zh";
    public static final String bioKey="bio";
    public static final String eduKey="edu";
    public static final String positionKey="position";
    public static final String workKey="work";

    public String id;
    public String avatar;
    public float activity;
    public int citations;
    public float diversity;
    public float gindex;
    public float hindex;
    public float newStar;
    public float risingStar;
    public float sociability;
    public boolean isPassedAway;
    public String name;
    public String nameZh;
    public String affiliation="";
    public String affiliationZh="";
    public String bio="";
    public String edu="";
    public String position="";
    public String work="";

    public void setId(String id)
    {
        this.id = id;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public void setActivity(float activity)
    {
        this.activity = activity;
    }

    public void setCitations(int citations)
    {
        this.citations = citations;
    }

    public void setDiversity(float diversity)
    {
        this.diversity = diversity;
    }

    public void setGindex(float gindex)
    {
        this.gindex = gindex;
    }

    public void setHindex(float hindex)
    {
        this.hindex = hindex;
    }

    public void setNewStar(float newStar)
    {
        this.newStar = newStar;
    }

    public void setRisingStar(float risingStar)
    {
        this.risingStar = risingStar;
    }

    public void setSociability(float sociability)
    {
        this.sociability = sociability;
    }

    public void setPassedAway(boolean passedAway)
    {
        isPassedAway = passedAway;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setNameZh(String nameZh)
    {
        this.nameZh = nameZh;
    }

    public void setAffiliation(String affiliation)
    {
        this.affiliation = affiliation;
    }

    public void setAffiliationZh(String affiliationZh)
    {
        this.affiliationZh = affiliationZh;
    }

    public void setBio(String bio)
    {
        this.bio = bio;
    }

    public void setEdu(String edu)
    {
        this.edu = edu;
    }

    public void setPosition(String position)
    {
        this.position = position;
    }

    public void setWork(String work)
    {
        this.work = work;
    }
}
