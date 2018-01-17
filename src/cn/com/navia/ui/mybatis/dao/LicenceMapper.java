// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LicenceMapper.java

package cn.com.navia.ui.mybatis.dao;

import cn.com.navia.ui.mybatis.model.Licence;
import cn.com.navia.ui.mybatis.model.LicenceExample;
import java.util.List;

public interface LicenceMapper
{

    public abstract int countByExample(LicenceExample licenceexample);

    public abstract int deleteByExample(LicenceExample licenceexample);

    public abstract int deleteByPrimaryKey(Integer integer);

    public abstract int insert(Licence licence);

    public abstract int insertSelective(Licence licence);

    public abstract List selectByExample(LicenceExample licenceexample);

    public abstract Licence selectByPrimaryKey(Integer integer);

    public abstract int updateByExampleSelective(Licence licence, LicenceExample licenceexample);

    public abstract int updateByExample(Licence licence, LicenceExample licenceexample);

    public abstract int updateByPrimaryKeySelective(Licence licence);

    public abstract int updateByPrimaryKey(Licence licence);
}
