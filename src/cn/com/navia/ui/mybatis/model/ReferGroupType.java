// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReferGroupType.java

package cn.com.navia.ui.mybatis.model;


public class ReferGroupType
{

    public ReferGroupType()
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

    public Integer getgType()
    {
        return gType;
    }

    public void setgType(Integer gType)
    {
        this.gType = gType;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    private Integer id;
    private Integer gType;
    private String name;
}
