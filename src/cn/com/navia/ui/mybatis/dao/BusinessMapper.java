// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BusinessMapper.java

package cn.com.navia.ui.mybatis.dao;

import cn.com.navia.ui.mybatis.model.Business;
import cn.com.navia.ui.mybatis.model.BusinessExample;
import java.util.List;

public interface BusinessMapper
{

    public abstract int countByExample(BusinessExample businessexample);

    public abstract int deleteByExample(BusinessExample businessexample);

    public abstract int deleteByPrimaryKey(Integer integer);

    public abstract int insert(Business business);

    public abstract int insertSelective(Business business);

    public abstract List selectByExample(BusinessExample businessexample);

    public abstract Business selectByPrimaryKey(Integer integer);

    public abstract int updateByExampleSelective(Business business, BusinessExample businessexample);

    public abstract int updateByExample(Business business, BusinessExample businessexample);

    public abstract int updateByPrimaryKeySelective(Business business);

    public abstract int updateByPrimaryKey(Business business);
}
