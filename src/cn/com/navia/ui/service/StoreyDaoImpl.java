// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StoreyDaoImpl.java

package cn.com.navia.ui.service;

import cn.com.navia.ui.mybatis.dao.StoreyMapper;
import cn.com.navia.ui.mybatis.model.StoreyExample;
import cn.com.navia.ui.spring.base.SpringModule;
import java.util.List;

public class StoreyDaoImpl extends SpringModule
{

    public StoreyDaoImpl()
    {
    }

    public List getStoreysByMallId(int mid)
        throws Exception
    {
        StoreyExample example = new StoreyExample();
        example.createCriteria().andMallIdEqualTo(Integer.valueOf(mid));
        return storeyMapper.selectByExample(example);
    }

    private StoreyMapper storeyMapper;
}
