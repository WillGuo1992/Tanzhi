// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReferAreaPointMapper.java

package cn.com.navia.ui.mybatis.dao;

import cn.com.navia.ui.mybatis.model.ReferAreaPoint;
import cn.com.navia.ui.mybatis.model.ReferAreaPointExample;
import java.util.List;

public interface ReferAreaPointMapper
{

    public abstract int countByExample(ReferAreaPointExample referareapointexample);

    public abstract int deleteByExample(ReferAreaPointExample referareapointexample);

    public abstract int deleteByPrimaryKey(Integer integer);

    public abstract int insert(ReferAreaPoint referareapoint);

    public abstract int insertSelective(ReferAreaPoint referareapoint);

    public abstract List selectByExample(ReferAreaPointExample referareapointexample);

    public abstract ReferAreaPoint selectByPrimaryKey(Integer integer);

    public abstract int updateByExampleSelective(ReferAreaPoint referareapoint, ReferAreaPointExample referareapointexample);

    public abstract int updateByExample(ReferAreaPoint referareapoint, ReferAreaPointExample referareapointexample);

    public abstract int updateByPrimaryKeySelective(ReferAreaPoint referareapoint);

    public abstract int updateByPrimaryKey(ReferAreaPoint referareapoint);
}
