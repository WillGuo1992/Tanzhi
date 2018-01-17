// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnalyseStayM.java

package cn.com.navia.ui.mybatis.model;

import java.util.Date;

public class AnalyseStayM
{

    public AnalyseStayM()
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

    public Integer getUnreapeatCount()
    {
        return unreapeatCount;
    }

    public void setUnreapeatCount(Integer unreapeatCount)
    {
        this.unreapeatCount = unreapeatCount;
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
    private Integer unreapeatCount;
    private Date analyTime;
    private Date dataTime;
    private Integer gId;
    private Integer bId;
}
