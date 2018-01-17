// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnalyseVendor.java

package cn.com.navia.ui.mybatis.model;

import java.util.Date;

public class AnalyseVendor
{

    public AnalyseVendor()
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

    public Integer getbId()
    {
        return bId;
    }

    public void setbId(Integer bId)
    {
        this.bId = bId;
    }

    public Date getDataTime()
    {
        return dataTime;
    }

    public void setDataTime(Date dataTime)
    {
        this.dataTime = dataTime;
    }

    public String getVendorName()
    {
        return vendorName;
    }

    public void setVendorName(String vendorName)
    {
        this.vendorName = vendorName;
    }

    public String getOsName()
    {
        return osName;
    }

    public void setOsName(String osName)
    {
        this.osName = osName;
    }

    public Integer getArrivalCount()
    {
        return arrivalCount;
    }

    public void setArrivalCount(Integer arrivalCount)
    {
        this.arrivalCount = arrivalCount;
    }

    private Integer id;
    private Integer gId;
    private Integer bId;
    private Date dataTime;
    private String vendorName;
    private String osName;
    private Integer arrivalCount;
}
