// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AreaPoint.java

package cn.com.navia.ui.mybatis.model;


public class AreaPoint
{

    public AreaPoint()
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

    public Integer getMapId()
    {
        return mapId;
    }

    public void setMapId(Integer mapId)
    {
        this.mapId = mapId;
    }

    public Integer getAreaId()
    {
        return areaId;
    }

    public void setAreaId(Integer areaId)
    {
        this.areaId = areaId;
    }

    public Double getLon()
    {
        return lon;
    }

    public void setLon(Double lon)
    {
        this.lon = lon;
    }

    public Double getLat()
    {
        return lat;
    }

    public void setLat(Double lat)
    {
        this.lat = lat;
    }

    public Integer getReferId()
    {
        return referId;
    }

    public void setReferId(Integer referId)
    {
        this.referId = referId;
    }

    public Integer getSort()
    {
        return sort;
    }

    public void setSort(Integer sort)
    {
        this.sort = sort;
    }

    private Integer id;
    private Integer mapId;
    private Integer areaId;
    private Double lon;
    private Double lat;
    private Integer referId;
    private Integer sort;
}
