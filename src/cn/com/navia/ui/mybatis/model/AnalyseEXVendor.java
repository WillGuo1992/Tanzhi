// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnalyseEXVendor.java

package cn.com.navia.ui.mybatis.model;

import java.util.Date;

public class AnalyseEXVendor
{

    public AnalyseEXVendor()
    {
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getgId()
    {
        return gId;
    }

    public void setgId(Integer gId)
    {
        this.gId = gId;
    }

    public Integer getAreaId()
    {
        return areaId;
    }

    public void setAreaId(Integer areaId)
    {
        this.areaId = areaId;
    }

    public Date getvTime()
    {
        return vTime;
    }

    public void setvTime(Date vTime)
    {
        this.vTime = vTime;
    }

    public String getVendor()
    {
        return vendor;
    }

    public void setVendor(String vendor)
    {
        this.vendor = vendor;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getCount()
    {
        return count;
    }

    public void setCount(Integer count)
    {
        this.count = count;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public Integer getIndoorCount()
    {
        return indoorCount;
    }

    public void setIndoorCount(Integer indoorCount)
    {
        this.indoorCount = indoorCount;
    }

    private Integer id;
    private Integer gId;
    private Integer areaId;
    private Date vTime;
    private String vendor;
    private String name;
    private Integer count;
    private String type;
    private Integer indoorCount;
}
