// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserDaoImpl.java

package cn.com.navia.ui.service;

import cn.com.navia.ui.mybatis.dao.UserMapper;
import cn.com.navia.ui.mybatis.model.User;
import cn.com.navia.ui.mybatis.model.UserExample;
import cn.com.navia.ui.spring.base.SpringModule;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends SpringModule
{

    public UserDaoImpl()
    {
    }

    public User getUserById(int id)
        throws Exception
    {
        return userMapper.selectByPrimaryKey(Integer.valueOf(id));
    }

    public List getUsersByloginfo(String info)
        throws Exception
    {
        List users = new ArrayList();
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(info);
        users = userMapper.selectByExample(example);
        if(users != null && users.size() > 0)
        {
            return users;
        } else
        {
            example = new UserExample();
            example.createCriteria().andTelephoneEqualTo(info);
            users = userMapper.selectByExample(example);
            return users;
        }
    }

    public List getUsersByMallId(int mallid)
        throws Exception
    {
        List users = new ArrayList();
        UserExample example = new UserExample();
        example.createCriteria().andMallIdEqualTo(Integer.valueOf(mallid));
        users = userMapper.selectByExample(example);
        return users;
    }

    public int addUserRecord(User u)
        throws Exception
    {
        return userMapper.insertSelective(u);
    }

    public int updateUser(User u)
        throws Exception
    {
        return userMapper.updateByPrimaryKey(u);
    }

    public int deleteUser(int uid)
        throws Exception
    {
        return userMapper.deleteByPrimaryKey(Integer.valueOf(uid));
    }

    private UserMapper userMapper;
}
