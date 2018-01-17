// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BusinessDaoImpl.java

package cn.com.navia.ui.service;

import cn.com.navia.ui.mybatis.dao.BusinessMapper;
import cn.com.navia.ui.mybatis.dao.BusinessTypeMapper;
import cn.com.navia.ui.mybatis.model.*;
import cn.com.navia.ui.spring.base.SpringModule;
import java.util.*;

public class BusinessDaoImpl extends SpringModule
{

    public BusinessDaoImpl()
    {
    }

    public List getAllBusinessType()
        throws Exception
    {
        BusinessTypeExample example = new BusinessTypeExample();
        example.createCriteria();
        return typeMapper.selectByExample(example);
    }

    public List getAllBusinessByGroupId(int groupId)
        throws Exception
    {
        BusinessExample example = new BusinessExample();
        example.createCriteria().andGIdEqualTo(Integer.valueOf(groupId));
        return businessMapper.selectByExample(example);
    }

    public List getAllBusinessTypeByGroupId(int groupId)
        throws Exception
    {
        List result = new ArrayList();
        BusinessExample bexample = new BusinessExample();
        bexample.createCriteria().andTopGidEqualTo(Integer.valueOf(groupId));
        List blist = businessMapper.selectByExample(bexample);
        if(blist != null && blist.size() > 0)
        {
            List tempTypes = new ArrayList();
            for(Iterator iterator = blist.iterator(); iterator.hasNext();)
            {
                Business business = (Business)iterator.next();
                if(!tempTypes.contains(business.getbType()))
                {
                    BusinessTypeExample btexample = new BusinessTypeExample();
                    btexample.createCriteria().andBTypeEqualTo(business.getbType());
                    List types = typeMapper.selectByExample(btexample);
                    if(types != null && types.size() > 0)
                        result.add((BusinessType)types.get(0));
                    tempTypes.add(business.getbType());
                }
            }

        }
        return result;
    }

    public List getBusinessByGidandType(int groupId, int businessType)
        throws Exception
    {
        BusinessExample example = new BusinessExample();
        example.createCriteria().andGIdEqualTo(Integer.valueOf(groupId)).andBTypeEqualTo(Integer.valueOf(businessType));
        return businessMapper.selectByExample(example);
    }

    private BusinessMapper businessMapper;
    private BusinessTypeMapper typeMapper;
}
