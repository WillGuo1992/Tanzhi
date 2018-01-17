// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnalyseEXVendorDaoImpl.java

package cn.com.navia.ui.service;

import cn.com.navia.ui.mybatis.dao.AnalyseEXVendorMapper;
import cn.com.navia.ui.mybatis.model.AnalyseEXVendor;
import cn.com.navia.ui.mybatis.model.AnalyseEXVendorExample;
import cn.com.navia.ui.spring.base.SpringModule;
import java.util.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

public class AnalyseEXVendorDaoImpl extends SpringModule
{
    public class ComparatorJSON
        implements Comparator
    {

        public int compare(Object arg0, Object arg1)
        {
            JSONObject obj0 = (JSONObject)arg0;
            JSONObject obj1 = (JSONObject)arg1;
            int count0 = obj0.getInt("counts");
            int count1 = obj1.getInt("counts");
            return count0 <= count1 ? 1 : -1;
        }

        final AnalyseEXVendorDaoImpl this$0;

        public ComparatorJSON()
        {
            this$0 = AnalyseEXVendorDaoImpl.this;
            super();
        }
    }


    public AnalyseEXVendorDaoImpl()
    {
    }

    public List getDeviceVendorRankByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        List ranks = new ArrayList();
        AnalyseEXVendorExample example = new AnalyseEXVendorExample();
        example.createCriteria().andVTimeBetween(startDate, endDate).andGIdEqualTo(Integer.valueOf(groupId)).andTypeEqualTo("1");
        List vendors = exVendorMapper.selectByExample(example);
        if(vendors.size() > 0)
        {
            for(Iterator iterator = vendors.iterator(); iterator.hasNext();)
            {
                AnalyseEXVendor vendor = (AnalyseEXVendor)iterator.next();
                String vendorName = vendor.getName();
                int vendorCount = vendor.getCount().intValue();
                if(ranks.size() > 0)
                {
                    boolean flag = false;
                    for(int i = 0; i < ranks.size(); i++)
                    {
                        JSONObject rank = (JSONObject)ranks.get(i);
                        if(rank.getString("name").equalsIgnoreCase(vendorName))
                        {
                            flag = true;
                            int c = rank.getInt("counts");
                            rank.put("counts", Integer.valueOf(c + vendorCount));
                        }
                    }

                    if(!flag)
                    {
                        JSONObject obj = new JSONObject();
                        obj.put("name", vendorName);
                        obj.put("counts", Integer.valueOf(vendorCount));
                        ranks.add(obj);
                    }
                } else
                if(vendorName != null)
                {
                    JSONObject obj = new JSONObject();
                    obj.put("name", vendorName);
                    obj.put("counts", Integer.valueOf(vendorCount));
                    ranks.add(obj);
                }
            }

        }
        ComparatorJSON comparator = new ComparatorJSON();
        Collections.sort(ranks, comparator);
        log.info("ranks:{}", ranks.toString());
        return ranks;
    }

    private AnalyseEXVendorMapper exVendorMapper;
}
