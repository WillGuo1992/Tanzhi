// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReferDeviceMapper.java

package cn.com.navia.ui.mybatis.dao;

import cn.com.navia.ui.mybatis.model.ReferDevice;
import cn.com.navia.ui.mybatis.model.ReferDeviceExample;
import java.util.List;

public interface ReferDeviceMapper
{

    public abstract int countByExample(ReferDeviceExample referdeviceexample);

    public abstract int deleteByExample(ReferDeviceExample referdeviceexample);

    public abstract int deleteByPrimaryKey(Integer integer);

    public abstract int insert(ReferDevice referdevice);

    public abstract int insertSelective(ReferDevice referdevice);

    public abstract List selectByExample(ReferDeviceExample referdeviceexample);

    public abstract ReferDevice selectByPrimaryKey(Integer integer);

    public abstract int updateByExampleSelective(ReferDevice referdevice, ReferDeviceExample referdeviceexample);

    public abstract int updateByExample(ReferDevice referdevice, ReferDeviceExample referdeviceexample);

    public abstract int updateByPrimaryKeySelective(ReferDevice referdevice);

    public abstract int updateByPrimaryKey(ReferDevice referdevice);
}
