// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnalyseStay.java

package cn.com.navia.ui.mybatis.model;

import java.util.Date;

public class AnalyseStay
{

    public AnalyseStay()
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

    public Integer getUnrepeatCount()
    {
        return unrepeatCount;
    }

    public void setUnrepeatCount(Integer unrepeatCount)
    {
        this.unrepeatCount = unrepeatCount;
    }

    public Integer getRepeatCount()
    {
        return repeatCount;
    }

    public void setRepeatCount(Integer repeatCount)
    {
        this.repeatCount = repeatCount;
    }

    public Integer getStayCount()
    {
        return stayCount;
    }

    public void setStayCount(Integer stayCount)
    {
        this.stayCount = stayCount;
    }

    public Integer getAvgStayTime()
    {
        return avgStayTime;
    }

    public void setAvgStayTime(Integer avgStayTime)
    {
        this.avgStayTime = avgStayTime;
    }

    public Date getAnalyTime()
    {
        return analyTime;
    }

    public void setAnalyTime(Date analyTime)
    {
        this.analyTime = analyTime;
    }

    private Integer id;
    private Integer gId;
    private Integer bId;
    private Date dataTime;
    private Integer unrepeatCount;
    private Integer repeatCount;
    private Integer stayCount;
    private Integer avgStayTime;
    private Date analyTime;
}
