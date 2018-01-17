// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReferMapMapper.java

package cn.com.navia.ui.mybatis.dao;

import cn.com.navia.ui.mybatis.model.ReferMap;
import cn.com.navia.ui.mybatis.model.ReferMapExample;
import java.util.List;

public interface ReferMapMapper
{

    public abstract int countByExample(ReferMapExample refermapexample);

    public abstract int deleteByExample(ReferMapExample refermapexample);

    public abstract int deleteByPrimaryKey(Integer integer);

    public abstract int insert(ReferMap refermap);

    public abstract int insertSelective(ReferMap refermap);

    public abstract List selectByExample(ReferMapExample refermapexample);

    public abstract ReferMap selectByPrimaryKey(Integer integer);

    public abstract int updateByExampleSelective(ReferMap refermap, ReferMapExample refermapexample);

    public abstract int updateByExample(ReferMap refermap, ReferMapExample refermapexample);

    public abstract int updateByPrimaryKeySelective(ReferMap refermap);

    public abstract int updateByPrimaryKey(ReferMap refermap);
}
