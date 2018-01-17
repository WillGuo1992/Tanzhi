// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Company.java

package cn.com.navia.ui.mybatis.model;


public class Company
{

    public Company()
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

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getLicenceCode()
    {
        return licenceCode;
    }

    public void setLicenceCode(String licenceCode)
    {
        this.licenceCode = licenceCode;
    }

    public Integer getGroupId()
    {
        return groupId;
    }

    public void setGroupId(Integer groupId)
    {
        this.groupId = groupId;
    }

    private Integer id;
    private String name;
    private String type;
    private String licenceCode;
    private Integer groupId;
}
