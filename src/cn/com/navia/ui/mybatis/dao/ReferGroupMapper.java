// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReferGroupMapper.java

package cn.com.navia.ui.mybatis.dao;

import cn.com.navia.ui.mybatis.model.ReferGroup;
import cn.com.navia.ui.mybatis.model.ReferGroupExample;
import java.util.List;

public interface ReferGroupMapper
{

    public abstract int countByExample(ReferGroupExample refergroupexample);

    public abstract int deleteByExample(ReferGroupExample refergroupexample);

    public abstract int deleteByPrimaryKey(Integer integer);

    public abstract int insert(ReferGroup refergroup);

    public abstract int insertSelective(ReferGroup refergroup);

    public abstract List selectByExample(ReferGroupExample refergroupexample);

    public abstract ReferGroup selectByPrimaryKey(Integer integer);

    public abstract int updateByExampleSelective(ReferGroup refergroup, ReferGroupExample refergroupexample);

    public abstract int updateByExample(ReferGroup refergroup, ReferGroupExample refergroupexample);

    public abstract int updateByPrimaryKeySelective(ReferGroup refergroup);

    public abstract int updateByPrimaryKey(ReferGroup refergroup);
}
