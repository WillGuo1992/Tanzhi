// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReferAreaMapper.java

package cn.com.navia.ui.mybatis.dao;

import cn.com.navia.ui.mybatis.model.ReferArea;
import cn.com.navia.ui.mybatis.model.ReferAreaExample;
import java.util.List;

public interface ReferAreaMapper
{

    public abstract int countByExample(ReferAreaExample referareaexample);

    public abstract int deleteByExample(ReferAreaExample referareaexample);

    public abstract int deleteByPrimaryKey(Integer integer);

    public abstract int insert(ReferArea referarea);

    public abstract int insertSelective(ReferArea referarea);

    public abstract List selectByExample(ReferAreaExample referareaexample);

    public abstract ReferArea selectByPrimaryKey(Integer integer);

    public abstract int updateByExampleSelective(ReferArea referarea, ReferAreaExample referareaexample);

    public abstract int updateByExample(ReferArea referarea, ReferAreaExample referareaexample);

    public abstract int updateByPrimaryKeySelective(ReferArea referarea);

    public abstract int updateByPrimaryKey(ReferArea referarea);
}
