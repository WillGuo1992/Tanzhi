// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnalyseStayW.java

package cn.com.navia.ui.mybatis.model;

import java.util.Date;

public class AnalyseStayW
{

    public AnalyseStayW()
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

    public Integer getUnrepeatCount()
    {
        return unrepeatCount;
    }

    public void setUnrepeatCount(Integer unrepeatCount)
    {
        this.unrepeatCount = unrepeatCount;
    }

    public Date getAnalyTime()
    {
        return analyTime;
    }

    public void setAnalyTime(Date analyTime)
    {
        this.analyTime = analyTime;
    }

    public Date getDataTime()
    {
        return dataTime;
    }

    public void setDataTime(Date dataTime)
    {
        this.dataTime = dataTime;
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

    private Integer id;
    private Integer unrepeatCount;
    private Date analyTime;
    private Date dataTime;
    private Integer gId;
    private Integer bId;
}
