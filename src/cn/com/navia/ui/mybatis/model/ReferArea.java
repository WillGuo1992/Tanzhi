// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReferArea.java

package cn.com.navia.ui.mybatis.model;


public class ReferArea
{

    public ReferArea()
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getgId()
    {
        return gId;
    }

    public void setgId(Integer gId)
    {
        this.gId = gId;
    }

    public Integer getMapId()
    {
        return mapId;
    }

    public void setMapId(Integer mapId)
    {
        this.mapId = mapId;
    }

    public Boolean getIsImportant()
    {
        return isImportant;
    }

    public void setIsImportant(Boolean isImportant)
    {
        this.isImportant = isImportant;
    }

    public Integer getaType()
    {
        return aType;
    }

    public void setaType(Integer aType)
    {
        this.aType = aType;
    }

    private Integer id;
    private String name;
    private Integer gId;
    private Integer mapId;
    private Boolean isImportant;
    private Integer aType;
}
