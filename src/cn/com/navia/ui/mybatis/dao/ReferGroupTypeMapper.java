// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReferGroupTypeMapper.java

package cn.com.navia.ui.mybatis.dao;

import cn.com.navia.ui.mybatis.model.ReferGroupType;
import cn.com.navia.ui.mybatis.model.ReferGroupTypeExample;
import java.util.List;

public interface ReferGroupTypeMapper
{

    public abstract int countByExample(ReferGroupTypeExample refergrouptypeexample);

    public abstract int deleteByExample(ReferGroupTypeExample refergrouptypeexample);

    public abstract int deleteByPrimaryKey(Integer integer);

    public abstract int insert(ReferGroupType refergrouptype);

    public abstract int insertSelective(ReferGroupType refergrouptype);

    public abstract List selectByExample(ReferGroupTypeExample refergrouptypeexample);

    public abstract ReferGroupType selectByPrimaryKey(Integer integer);

    public abstract int updateByExampleSelective(ReferGroupType refergrouptype, ReferGroupTypeExample refergrouptypeexample);

    public abstract int updateByExample(ReferGroupType refergrouptype, ReferGroupTypeExample refergrouptypeexample);

    public abstract int updateByPrimaryKeySelective(ReferGroupType refergrouptype);

    public abstract int updateByPrimaryKey(ReferGroupType refergrouptype);
}
