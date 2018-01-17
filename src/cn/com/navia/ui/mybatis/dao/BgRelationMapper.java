// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BgRelationMapper.java

package cn.com.navia.ui.mybatis.dao;

import cn.com.navia.ui.mybatis.model.BgRelation;
import cn.com.navia.ui.mybatis.model.BgRelationExample;
import java.util.List;

public interface BgRelationMapper
{

    public abstract int countByExample(BgRelationExample bgrelationexample);

    public abstract int deleteByExample(BgRelationExample bgrelationexample);

    public abstract int deleteByPrimaryKey(Integer integer);

    public abstract int insert(BgRelation bgrelation);

    public abstract int insertSelective(BgRelation bgrelation);

    public abstract List selectByExample(BgRelationExample bgrelationexample);

    public abstract BgRelation selectByPrimaryKey(Integer integer);

    public abstract int updateByExampleSelective(BgRelation bgrelation, BgRelationExample bgrelationexample);

    public abstract int updateByExample(BgRelation bgrelation, BgRelationExample bgrelationexample);

    public abstract int updateByPrimaryKeySelective(BgRelation bgrelation);

    public abstract int updateByPrimaryKey(BgRelation bgrelation);
}
