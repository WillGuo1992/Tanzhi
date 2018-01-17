// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ManagerMapper.java

package cn.com.navia.ui.mybatis.dao;

import cn.com.navia.ui.mybatis.model.Manager;
import cn.com.navia.ui.mybatis.model.ManagerExample;
import java.util.List;

public interface ManagerMapper
{

    public abstract int countByExample(ManagerExample managerexample);

    public abstract int deleteByExample(ManagerExample managerexample);

    public abstract int deleteByPrimaryKey(Integer integer);

    public abstract int insert(Manager manager);

    public abstract int insertSelective(Manager manager);

    public abstract List selectByExample(ManagerExample managerexample);

    public abstract Manager selectByPrimaryKey(Integer integer);

    public abstract int updateByExampleSelective(Manager manager, ManagerExample managerexample);

    public abstract int updateByExample(Manager manager, ManagerExample managerexample);

    public abstract int updateByPrimaryKeySelective(Manager manager);

    public abstract int updateByPrimaryKey(Manager manager);
}
