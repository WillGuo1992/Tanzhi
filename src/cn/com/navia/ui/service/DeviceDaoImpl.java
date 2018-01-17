// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DeviceDaoImpl.java

package cn.com.navia.ui.service;

import cn.com.navia.ui.mybatis.dao.DeviceMapper;
import cn.com.navia.ui.mybatis.model.Device;
import cn.com.navia.ui.mybatis.model.DeviceExample;
import cn.com.navia.ui.spring.base.SpringModule;
import java.util.List;

public class DeviceDaoImpl extends SpringModule
{

    public DeviceDaoImpl()
    {
    }

    public Device getDeviceById(int did)
        throws Exception
    {
        return deviceMapper.selectByPrimaryKey(Integer.valueOf(did));
    }

    public List getDevicesByMallId(int mallid)
        throws Exception
    {
        DeviceExample example = new DeviceExample();
        example.createCriteria().andMallIdEqualTo(Integer.valueOf(mallid)).andEnableEqualTo(Boolean.valueOf(true));
        return deviceMapper.selectByExample(example);
    }

    public List getDeviceByMac(String mac)
        throws Exception
    {
        DeviceExample example = new DeviceExample();
        example.createCriteria().andMacEqualTo(mac).andEnableEqualTo(Boolean.valueOf(true));
        return deviceMapper.selectByExample(example);
    }

    public int updateDevice(Device device)
        throws Exception
    {
        return deviceMapper.updateByPrimaryKey(device);
    }

    public int addDevice(Device device)
        throws Exception
    {
        return deviceMapper.insertSelective(device);
    }

    public int deleteDevice(int did)
        throws Exception
    {
        return deviceMapper.deleteByPrimaryKey(Integer.valueOf(did));
    }

    private DeviceMapper deviceMapper;
}
