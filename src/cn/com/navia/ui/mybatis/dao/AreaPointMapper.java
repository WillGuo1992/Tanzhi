// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AreaPointMapper.java

package cn.com.navia.ui.mybatis.dao;

import cn.com.navia.ui.mybatis.model.AreaPoint;
import cn.com.navia.ui.mybatis.model.AreaPointExample;
import java.util.List;

public interface AreaPointMapper
{

    public abstract int countByExample(AreaPointExample areapointexample);

    public abstract int deleteByExample(AreaPointExample areapointexample);

    public abstract int deleteByPrimaryKey(Integer integer);

    public abstract int insert(AreaPoint areapoint);

    public abstract int insertSelective(AreaPoint areapoint);

    public abstract List selectByExample(AreaPointExample areapointexample);

    public abstract AreaPoint selectByPrimaryKey(Integer integer);

    public abstract int updateByExampleSelective(AreaPoint areapoint, AreaPointExample areapointexample);

    public abstract int updateByExample(AreaPoint areapoint, AreaPointExample areapointexample);

    public abstract int updateByPrimaryKeySelective(AreaPoint areapoint);

    public abstract int updateByPrimaryKey(AreaPoint areapoint);
}
