// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ManagerDaoImpl.java

package cn.com.navia.ui.service;

import cn.com.navia.ui.mybatis.dao.ManagerMapper;
import cn.com.navia.ui.mybatis.model.Manager;
import cn.com.navia.ui.mybatis.model.ManagerExample;
import cn.com.navia.ui.spring.base.SpringModule;
import java.util.ArrayList;
import java.util.List;

public class ManagerDaoImpl extends SpringModule
{

    public ManagerDaoImpl()
    {
    }

    public Manager getManagerById(int id)
        throws Exception
    {
        return managerMapper.selectByPrimaryKey(Integer.valueOf(id));
    }

    public List getManagerByLoginfo(String info)
        throws Exception
    {
        List managers = new ArrayList();
        ManagerExample example = new ManagerExample();
        example.createCriteria().andUsernameEqualTo(info);
        managers = managerMapper.selectByExample(example);
        if(managers != null && managers.size() > 0)
        {
            return managers;
        } else
        {
            example = new ManagerExample();
            example.createCriteria().andTelephoneEqualTo(info);
            managers = managerMapper.selectByExample(example);
            return managers;
        }
    }

    public int createManager(Manager m)
        throws Exception
    {
        return managerMapper.insertSelective(m);
    }

    public List getManagerByEmail(String email)
        throws Exception
    {
        ManagerExample example = new ManagerExample();
        example.createCriteria().andEmailEqualTo(email);
        return managerMapper.selectByExample(example);
    }

    public List getManagerByUserName(String username)
        throws Exception
    {
        ManagerExample example = new ManagerExample();
        example.createCriteria().andUsernameEqualTo(username);
        return managerMapper.selectByExample(example);
    }

    public List getManagerByTelephone(String telephone)
        throws Exception
    {
        ManagerExample example = new ManagerExample();
        example.createCriteria().andTelephoneEqualTo(telephone);
        return managerMapper.selectByExample(example);
    }

    public int updateManager(Manager manager)
        throws Exception
    {
        return managerMapper.updateByPrimaryKey(manager);
    }

    public List getManagerByMallId(int mallId)
        throws Exception
    {
        ManagerExample example = new ManagerExample();
        example.createCriteria().andMallIdEqualTo(Integer.valueOf(mallId));
        return managerMapper.selectByExample(example);
    }

    private ManagerMapper managerMapper;
}
