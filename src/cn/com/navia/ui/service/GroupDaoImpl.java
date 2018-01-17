// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GroupDaoImpl.java

package cn.com.navia.ui.service;

import cn.com.navia.ui.mybatis.dao.ReferGroupMapper;
import cn.com.navia.ui.mybatis.model.ReferGroup;
import cn.com.navia.ui.spring.base.SpringModule;

public class GroupDaoImpl extends SpringModule
{

    public GroupDaoImpl()
    {
    }

    public ReferGroup getGroupById(int gid)
        throws Exception
    {
        return groupMapper.selectByPrimaryKey(Integer.valueOf(gid));
    }

    public int CreateGroup(String name, int size, int parent_id, boolean isLeaf, int type)
        throws Exception
    {
        int g_id = 0;
        ReferGroup group = new ReferGroup();
        group.setName(name);
        group.setSize(Integer.valueOf(size));
        group.setParentId(Integer.valueOf(parent_id));
        group.setLeaf(Boolean.valueOf(isLeaf));
        group.setgType(Integer.valueOf(type));
        int selective = groupMapper.insertSelective(group);
        if(selective != 0)
            g_id = group.getId().intValue();
        return g_id;
    }

    public int updateGroup(ReferGroup g)
        throws Exception
    {
        return groupMapper.updateByPrimaryKey(g);
    }

    private ReferGroupMapper groupMapper;
}
