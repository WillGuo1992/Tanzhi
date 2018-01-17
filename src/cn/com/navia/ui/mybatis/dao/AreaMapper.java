// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AreaMapper.java

package cn.com.navia.ui.mybatis.dao;

import cn.com.navia.ui.mybatis.model.Area;
import cn.com.navia.ui.mybatis.model.AreaExample;
import java.util.List;

public interface AreaMapper
{

    public abstract int countByExample(AreaExample areaexample);

    public abstract int deleteByExample(AreaExample areaexample);

    public abstract int deleteByPrimaryKey(Integer integer);

    public abstract int insert(Area area);

    public abstract int insertSelective(Area area);

    public abstract List selectByExample(AreaExample areaexample);

    public abstract Area selectByPrimaryKey(Integer integer);

    public abstract int updateByExampleSelective(Area area, AreaExample areaexample);

    public abstract int updateByExample(Area area, AreaExample areaexample);

    public abstract int updateByPrimaryKeySelective(Area area);

    public abstract int updateByPrimaryKey(Area area);
}
