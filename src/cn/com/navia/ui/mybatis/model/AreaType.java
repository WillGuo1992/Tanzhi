// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AreaType.java

package cn.com.navia.ui.mybatis.model;


public class AreaType
{

    public AreaType()
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

    public Integer getaType()
    {
        return aType;
    }

    public void setaType(Integer aType)
    {
        this.aType = aType;
    }

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    private Integer id;
    private Integer aType;
    private String typeName;
}
