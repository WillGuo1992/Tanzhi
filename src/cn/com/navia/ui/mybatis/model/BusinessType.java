// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BusinessType.java

package cn.com.navia.ui.mybatis.model;


public class BusinessType
{

    public BusinessType()
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

    private Integer id;
    private Integer bType;
    private String bName;
}
