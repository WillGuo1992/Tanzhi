// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnalyseActive.java

package cn.com.navia.ui.mybatis.model;

import java.util.Date;

public class AnalyseActive
{

    public AnalyseActive()
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

    public Integer getHistoryCount()
    {
        return historyCount;
    }

    public void setHistoryCount(Integer historyCount)
    {
        this.historyCount = historyCount;
    }

    public Integer getActiveCount()
    {
        return activeCount;
    }

    public void setActiveCount(Integer activeCount)
    {
        this.activeCount = activeCount;
    }

    public Integer getCurActiveCount()
    {
        return curActiveCount;
    }

    public void setCurActiveCount(Integer curActiveCount)
    {
        this.curActiveCount = curActiveCount;
    }

    public Integer getNewCount()
    {
        return newCount;
    }

    public void setNewCount(Integer newCount)
    {
        this.newCount = newCount;
    }

    public Integer getLostCount()
    {
        return lostCount;
    }

    public void setLostCount(Integer lostCount)
    {
        this.lostCount = lostCount;
    }

    public Integer getReActiveCount()
    {
        return reActiveCount;
    }

    public void setReActiveCount(Integer reActiveCount)
    {
        this.reActiveCount = reActiveCount;
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
    private Integer historyCount;
    private Integer activeCount;
    private Integer curActiveCount;
    private Integer newCount;
    private Integer lostCount;
    private Integer reActiveCount;
    private Date analyTime;
}
