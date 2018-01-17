// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Business.java

package cn.com.navia.ui.mybatis.model;

import java.util.Date;

public class Business
{

    public Business()
    {
    }

    public Integer getbId()
    {
        return bId;
    }

    public void setbId(Integer bId)
    {
        this.bId = bId;
    }

    public Integer getbType()
    {
        return bType;
    }

    public void setbType(Integer bType)
    {
        this.bType = bType;
    }

    public String getbName()
    {
        return bName;
    }

    public void setbName(String bName)
    {
        this.bName = bName;
    }

    public String getgName()
    {
        return gName;
    }

    public void setgName(String gName)
    {
        this.gName = gName;
    }

    public Integer getgId()
    {
        return gId;
    }

    public void setgId(Integer gId)
    {
        this.gId = gId;
    }

    public Integer getTopGid()
    {
        return topGid;
    }

    public void setTopGid(Integer topGid)
    {
        this.topGid = topGid;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    private Integer bId;
    private Integer bType;
    private String bName;
    private String gName;
    private Integer gId;
    private Integer topGid;
    private Date createTime;
}
