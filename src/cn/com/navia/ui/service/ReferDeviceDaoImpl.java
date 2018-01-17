// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReferDeviceDaoImpl.java

package cn.com.navia.ui.service;

import cn.com.navia.ui.mybatis.dao.ReferDeviceMapper;
import cn.com.navia.ui.mybatis.model.ReferDevice;
import cn.com.navia.ui.mybatis.model.ReferDeviceExample;
import cn.com.navia.ui.spring.base.SpringModule;
import java.util.List;

public class ReferDeviceDaoImpl extends SpringModule
{

    public ReferDeviceDaoImpl()
    {
    }

    public ReferDevice getDeviceById(int did)
        throws Exception
    {
        return deviceMapper.selectByPrimaryKey(Integer.valueOf(did));
    }

    public List getDeviceByMac(String mac)
        throws Exception
    {
        ReferDeviceExample example = new ReferDeviceExample();
        example.createCriteria().andMacEqualTo(mac).andEnableEqualTo(Boolean.valueOf(true));
        return deviceMapper.selectByExample(example);
    }

    public int updateDevice(ReferDevice device)
        throws Exception
    {
        return deviceMapper.updateByPrimaryKey(device);
    }

    public int addReferDevice(ReferDevice device)
        throws Exception
    {
        return deviceMapper.insertSelective(device);
    }

    public int deleteDevice(int did)
        throws Exception
    {
        return deviceMapper.deleteByPrimaryKey(Integer.valueOf(did));
    }

    private ReferDeviceMapper deviceMapper;
}
