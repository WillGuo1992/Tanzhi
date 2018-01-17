// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AreaTypeMapper.java

package cn.com.navia.ui.mybatis.dao;

import cn.com.navia.ui.mybatis.model.AreaType;
import cn.com.navia.ui.mybatis.model.AreaTypeExample;
import java.util.List;

public interface AreaTypeMapper
{

    public abstract int countByExample(AreaTypeExample areatypeexample);

    public abstract int deleteByExample(AreaTypeExample areatypeexample);

    public abstract int deleteByPrimaryKey(Integer integer);

    public abstract int insert(AreaType areatype);

    public abstract int insertSelective(AreaType areatype);

    public abstract List selectByExample(AreaTypeExample areatypeexample);

    public abstract AreaType selectByPrimaryKey(Integer integer);

    public abstract int updateByExampleSelective(AreaType areatype, AreaTypeExample areatypeexample);

    public abstract int updateByExample(AreaType areatype, AreaTypeExample areatypeexample);

    public abstract int updateByPrimaryKeySelective(AreaType areatype);

    public abstract int updateByPrimaryKey(AreaType areatype);
}
