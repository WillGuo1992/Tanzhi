// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MallDaoImpl.java

package cn.com.navia.ui.service;

import cn.com.navia.ui.mybatis.dao.MallMapper;
import cn.com.navia.ui.mybatis.model.Mall;
import cn.com.navia.ui.mybatis.model.MallExample;
import cn.com.navia.ui.spring.base.SpringModule;
import java.util.List;

public class MallDaoImpl extends SpringModule
{

    public MallDaoImpl()
    {
    }

    public Mall getMallById(int mid)
        throws Exception
    {
        return mallMapper.selectByPrimaryKey(Integer.valueOf(mid));
    }

    public List getMallsByCompanyId(int cid)
        throws Exception
    {
        MallExample example = new MallExample();
        example.createCriteria().andCompanyIdEqualTo(Integer.valueOf(cid));
        return mallMapper.selectByExample(example);
    }

    public List getMallsByGradationId(int gid)
        throws Exception
    {
        MallExample example = new MallExample();
        example.createCriteria().andGradationIdEqualTo(Integer.valueOf(gid));
        return mallMapper.selectByExample(example);
    }

    public List getMallsByGroupId(int gid)
        throws Exception
    {
        MallExample example = new MallExample();
        example.createCriteria().andGroupIdEqualTo(Integer.valueOf(gid));
        return mallMapper.selectByExample(example);
    }

    public int createMall(int groupId)
        throws Exception
    {
        int mallId = 0;
        Mall m = new Mall();
        m.setGroupId(Integer.valueOf(groupId));
        m.setName(" ");
        int i = mallMapper.insertSelective(m);
        if(i != 0)
            mallId = m.getId().intValue();
        return mallId;
    }

    public int updateMallInfo(Mall m)
        throws Exception
    {
        return mallMapper.updateByPrimaryKey(m);
    }

    private MallMapper mallMapper;
}
