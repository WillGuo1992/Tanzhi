// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BusinessTypeMapper.java

package cn.com.navia.ui.mybatis.dao;

import cn.com.navia.ui.mybatis.model.BusinessType;
import cn.com.navia.ui.mybatis.model.BusinessTypeExample;
import java.util.List;

public interface BusinessTypeMapper
{

    public abstract int countByExample(BusinessTypeExample businesstypeexample);

    public abstract int deleteByExample(BusinessTypeExample businesstypeexample);

    public abstract int deleteByPrimaryKey(Integer integer);

    public abstract int insert(BusinessType businesstype);

    public abstract int insertSelective(BusinessType businesstype);

    public abstract List selectByExample(BusinessTypeExample businesstypeexample);

    public abstract BusinessType selectByPrimaryKey(Integer integer);

    public abstract int updateByExampleSelective(BusinessType businesstype, BusinessTypeExample businesstypeexample);

    public abstract int updateByExample(BusinessType businesstype, BusinessTypeExample businesstypeexample);

    public abstract int updateByPrimaryKeySelective(BusinessType businesstype);

    public abstract int updateByPrimaryKey(BusinessType businesstype);
}
