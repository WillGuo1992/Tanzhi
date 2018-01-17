// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReferAreaTypeMapper.java

package cn.com.navia.ui.mybatis.dao;

import cn.com.navia.ui.mybatis.model.ReferAreaType;
import cn.com.navia.ui.mybatis.model.ReferAreaTypeExample;
import java.util.List;

public interface ReferAreaTypeMapper
{

    public abstract int countByExample(ReferAreaTypeExample referareatypeexample);

    public abstract int deleteByExample(ReferAreaTypeExample referareatypeexample);

    public abstract int deleteByPrimaryKey(Integer integer);

    public abstract int insert(ReferAreaType referareatype);

    public abstract int insertSelective(ReferAreaType referareatype);

    public abstract List selectByExample(ReferAreaTypeExample referareatypeexample);

    public abstract ReferAreaType selectByPrimaryKey(Integer integer);

    public abstract int updateByExampleSelective(ReferAreaType referareatype, ReferAreaTypeExample referareatypeexample);

    public abstract int updateByExample(ReferAreaType referareatype, ReferAreaTypeExample referareatypeexample);

    public abstract int updateByPrimaryKeySelective(ReferAreaType referareatype);

    public abstract int updateByPrimaryKey(ReferAreaType referareatype);
}
