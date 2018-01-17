// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Area.java

package cn.com.navia.ui.mybatis.model;


public class Area
{

    public Area()
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

    public Integer getMapId()
    {
        return mapId;
    }

    public void setMapId(Integer mapId)
    {
        this.mapId = mapId;
    }

    public Integer getMallId()
    {
        return mallId;
    }

    public void setMallId(Integer mallId)
    {
        this.mallId = mallId;
    }

    public Integer getReferId()
    {
        return referId;
    }

    public void setReferId(Integer referId)
    {
        this.referId = referId;
    }

    public Integer getGroupId()
    {
        return groupId;
    }

    public void setGroupId(Integer groupId)
    {
        this.groupId = groupId;
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

    public String getPoiId()
    {
        return poiId;
    }

    public void setPoiId(String poiId)
    {
        this.poiId = poiId;
    }

    private Integer id;
    private String name;
    private Integer mapId;
    private Integer mallId;
    private Integer referId;
    private Integer groupId;
    private Boolean isImportant;
    private Integer aType;
    private String poiId;
}
