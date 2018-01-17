// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserMapper.java

package cn.com.navia.ui.mybatis.dao;

import cn.com.navia.ui.mybatis.model.User;
import cn.com.navia.ui.mybatis.model.UserExample;
import java.util.List;

public interface UserMapper
{

    public abstract int countByExample(UserExample userexample);

    public abstract int deleteByExample(UserExample userexample);

    public abstract int deleteByPrimaryKey(Integer integer);

    public abstract int insert(User user);

    public abstract int insertSelective(User user);

    public abstract List selectByExample(UserExample userexample);

    public abstract User selectByPrimaryKey(Integer integer);

    public abstract int updateByExampleSelective(User user, UserExample userexample);

    public abstract int updateByExample(User user, UserExample userexample);

    public abstract int updateByPrimaryKeySelective(User user);

    public abstract int updateByPrimaryKey(User user);
}
