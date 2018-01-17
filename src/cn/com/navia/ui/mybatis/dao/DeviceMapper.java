// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DeviceMapper.java

package cn.com.navia.ui.mybatis.dao;

import cn.com.navia.ui.mybatis.model.Device;
import cn.com.navia.ui.mybatis.model.DeviceExample;
import java.util.List;

public interface DeviceMapper
{

    public abstract int countByExample(DeviceExample deviceexample);

    public abstract int deleteByExample(DeviceExample deviceexample);

    public abstract int deleteByPrimaryKey(Integer integer);

    public abstract int insert(Device device);

    public abstract int insertSelective(Device device);

    public abstract List selectByExample(DeviceExample deviceexample);

    public abstract Device selectByPrimaryKey(Integer integer);

    public abstract int updateByExampleSelective(Device device, DeviceExample deviceexample);

    public abstract int updateByExample(Device device, DeviceExample deviceexample);

    public abstract int updateByPrimaryKeySelective(Device device);

    public abstract int updateByPrimaryKey(Device device);
}
