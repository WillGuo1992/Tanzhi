// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StoreyMapper.java

package cn.com.navia.ui.mybatis.dao;

import cn.com.navia.ui.mybatis.model.Storey;
import cn.com.navia.ui.mybatis.model.StoreyExample;
import java.util.List;

public interface StoreyMapper
{

    public abstract int countByExample(StoreyExample storeyexample);

    public abstract int deleteByExample(StoreyExample storeyexample);

    public abstract int deleteByPrimaryKey(Integer integer);

    public abstract int insert(Storey storey);

    public abstract int insertSelective(Storey storey);

    public abstract List selectByExample(StoreyExample storeyexample);

    public abstract Storey selectByPrimaryKey(Integer integer);

    public abstract int updateByExampleSelective(Storey storey, StoreyExample storeyexample);

    public abstract int updateByExample(Storey storey, StoreyExample storeyexample);

    public abstract int updateByPrimaryKeySelective(Storey storey);

    public abstract int updateByPrimaryKey(Storey storey);
}
