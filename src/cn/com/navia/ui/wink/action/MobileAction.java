// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MobileAction.java

package cn.com.navia.ui.wink.action;

import cn.com.navia.ui.mybatis.model.AnalyseVendor;
import cn.com.navia.ui.service.AnalyseVendorDaoImpl;
import cn.com.navia.ui.utils.NaviaDateUtil;
import cn.com.navia.ui.wink.base.BaseAction;
import java.text.SimpleDateFormat;
import java.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.context.ApplicationContext;

public class MobileAction extends BaseAction
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

        final MobileAction this$0;

        public ComparatorJSON()
        {
            this$0 = MobileAction.this;
            super();
        }
    }


    public MobileAction()
    {
    }

    public String getVenderNoCompareBar(String startDate, String endDate, String groupId)
    {
        JSONObject obj = new JSONObject();
        AnalyseVendorDaoImpl vendorDao = (AnalyseVendorDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseVendorDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(groupId))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                List ranks = new ArrayList();
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                List vendors = vendorDao.getVendorsByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId));
                if(vendors != null && vendors.size() > 0)
                {
                    for(Iterator iterator = vendors.iterator(); iterator.hasNext();)
                    {
                        AnalyseVendor vendor = (AnalyseVendor)iterator.next();
                        String vendorName = vendor.getVendorName();
                        int vendorCount = vendor.getArrivalCount().intValue();
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
                                JSONObject data = new JSONObject();
                                data.put("name", vendorName);
                                data.put("counts", Integer.valueOf(vendorCount));
                                ranks.add(data);
                            }
                        } else
                        if(vendorName != null)
                        {
                            JSONObject data = new JSONObject();
                            data.put("name", vendorName);
                            data.put("counts", Integer.valueOf(vendorCount));
                            ranks.add(data);
                        }
                    }

                    ComparatorJSON comparator = new ComparatorJSON();
                    Collections.sort(ranks, comparator);
                    obj.put("code", Integer.valueOf(0));
                    obj.put("datas", ranks);
                } else
                {
                    obj.put("code", Integer.valueOf(-2));
                }
            } else
            {
                obj.put("code", Integer.valueOf(-1));
            }
        }
        catch(Exception e)
        {
            obj.put("code", Integer.valueOf(-9));
            e.printStackTrace();
        }
        return obj.toString();
    }

    public String getVenderCompareBar(String startDate, String endDate, String groupId, String compareStartDate)
    {
        JSONObject obj = new JSONObject();
        AnalyseVendorDaoImpl vendorDao = (AnalyseVendorDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseVendorDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(groupId))
            {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                String compareEndDate = format2.format(DateUtils.addDays(format2.parse(compareStartDate), dates.size() - 1));
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                compareStartDate = (new StringBuilder(String.valueOf(compareStartDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                compareEndDate = (new StringBuilder(String.valueOf(compareEndDate))).append(" 23:59:59").toString();
                List ranks = new ArrayList();
                List vendors = vendorDao.getVendorsByDate(format1.parse(startDate), format1.parse(endDate), Integer.parseInt(groupId));
                List com_vendors = vendorDao.getVendorsByDate(format1.parse(compareStartDate), format1.parse(compareEndDate), Integer.parseInt(groupId));
                if(vendors != null && vendors.size() > 0)
                {
                    for(Iterator iterator = vendors.iterator(); iterator.hasNext();)
                    {
                        AnalyseVendor vendor = (AnalyseVendor)iterator.next();
                        String vendorName = vendor.getVendorName();
                        int vendorCount = vendor.getArrivalCount().intValue();
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
                                    rank.put("compare_count", Integer.valueOf(0));
                                }
                            }

                            if(!flag)
                            {
                                JSONObject data = new JSONObject();
                                data.put("name", vendorName);
                                data.put("counts", Integer.valueOf(vendorCount));
                                data.put("compare_count", Integer.valueOf(0));
                                ranks.add(data);
                            }
                        } else
                        if(vendorName != null)
                        {
                            JSONObject data = new JSONObject();
                            data.put("name", vendorName);
                            data.put("counts", Integer.valueOf(vendorCount));
                            data.put("compare_count", Integer.valueOf(0));
                            ranks.add(data);
                        }
                    }

                    for(int j = 0; j < ranks.size(); j++)
                    {
                        JSONObject r = (JSONObject)ranks.get(j);
                        for(Iterator iterator1 = com_vendors.iterator(); iterator1.hasNext();)
                        {
                            AnalyseVendor com_vendor = (AnalyseVendor)iterator1.next();
                            if(com_vendor.getVendorName().equalsIgnoreCase(r.getString("name")))
                            {
                                int co = r.getInt("compare_count");
                                r.put("compare_count", Integer.valueOf(co + com_vendor.getArrivalCount().intValue()));
                            }
                        }

                    }

                    ComparatorJSON comparator = new ComparatorJSON();
                    Collections.sort(ranks, comparator);
                    obj.put("code", Integer.valueOf(0));
                    obj.put("datas", ranks);
                } else
                {
                    obj.put("code", Integer.valueOf(-2));
                }
            } else
            {
                obj.put("code", Integer.valueOf(-1));
            }
        }
        catch(Exception e)
        {
            obj.put("code", Integer.valueOf(-9));
            e.printStackTrace();
        }
        return obj.toString();
    }

    public String getOsNoCompareBar(String startDate, String endDate, String groupId)
    {
        JSONObject obj = new JSONObject();
        AnalyseVendorDaoImpl vendorDao = (AnalyseVendorDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseVendorDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(groupId))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                List ranks = new ArrayList();
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                List vendors = vendorDao.getVendorsByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId));
                if(vendors != null && vendors.size() > 0)
                {
                    for(Iterator iterator = vendors.iterator(); iterator.hasNext();)
                    {
                        AnalyseVendor vendor = (AnalyseVendor)iterator.next();
                        String osName = vendor.getOsName();
                        int vendorCount = vendor.getArrivalCount().intValue();
                        if(ranks.size() > 0)
                        {
                            boolean flag = false;
                            for(int i = 0; i < ranks.size(); i++)
                            {
                                JSONObject rank = (JSONObject)ranks.get(i);
                                if(rank.getString("name").equalsIgnoreCase(osName))
                                {
                                    flag = true;
                                    int c = rank.getInt("counts");
                                    rank.put("counts", Integer.valueOf(c + vendorCount));
                                }
                            }

                            if(!flag)
                            {
                                JSONObject data = new JSONObject();
                                data.put("name", osName);
                                data.put("counts", Integer.valueOf(vendorCount));
                                ranks.add(data);
                            }
                        } else
                        if(osName != null)
                        {
                            JSONObject data = new JSONObject();
                            data.put("name", osName);
                            data.put("counts", Integer.valueOf(vendorCount));
                            ranks.add(data);
                        }
                    }

                    ComparatorJSON comparator = new ComparatorJSON();
                    Collections.sort(ranks, comparator);
                    obj.put("code", Integer.valueOf(0));
                    obj.put("datas", ranks);
                } else
                {
                    obj.put("code", Integer.valueOf(-2));
                }
            } else
            {
                obj.put("code", Integer.valueOf(-1));
            }
        }
        catch(Exception e)
        {
            obj.put("code", Integer.valueOf(-9));
            e.printStackTrace();
        }
        return obj.toString();
    }

    public String getOsCompareBar(String startDate, String endDate, String groupId, String compareStartDate)
    {
        JSONObject obj = new JSONObject();
        AnalyseVendorDaoImpl vendorDao = (AnalyseVendorDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseVendorDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(groupId))
            {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                String compareEndDate = format2.format(DateUtils.addDays(format2.parse(compareStartDate), dates.size() - 1));
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                compareStartDate = (new StringBuilder(String.valueOf(compareStartDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                compareEndDate = (new StringBuilder(String.valueOf(compareEndDate))).append(" 23:59:59").toString();
                List ranks = new ArrayList();
                List vendors = vendorDao.getVendorsByDate(format1.parse(startDate), format1.parse(endDate), Integer.parseInt(groupId));
                List com_vendors = vendorDao.getVendorsByDate(format1.parse(compareStartDate), format1.parse(compareEndDate), Integer.parseInt(groupId));
                if(vendors != null && vendors.size() > 0)
                {
                    for(Iterator iterator = vendors.iterator(); iterator.hasNext();)
                    {
                        AnalyseVendor vendor = (AnalyseVendor)iterator.next();
                        String osName = vendor.getOsName();
                        int vendorCount = vendor.getArrivalCount().intValue();
                        if(ranks.size() > 0)
                        {
                            boolean flag = false;
                            for(int i = 0; i < ranks.size(); i++)
                            {
                                JSONObject rank = (JSONObject)ranks.get(i);
                                if(rank.getString("name").equalsIgnoreCase(osName))
                                {
                                    flag = true;
                                    int c = rank.getInt("counts");
                                    rank.put("counts", Integer.valueOf(c + vendorCount));
                                    rank.put("compare_count", Integer.valueOf(0));
                                }
                            }

                            if(!flag)
                            {
                                JSONObject data = new JSONObject();
                                data.put("name", osName);
                                data.put("counts", Integer.valueOf(vendorCount));
                                data.put("compare_count", Integer.valueOf(0));
                                ranks.add(data);
                            }
                        } else
                        if(osName != null)
                        {
                            JSONObject data = new JSONObject();
                            data.put("name", osName);
                            data.put("counts", Integer.valueOf(vendorCount));
                            data.put("compare_count", Integer.valueOf(0));
                            ranks.add(data);
                        }
                    }

                    for(int j = 0; j < ranks.size(); j++)
                    {
                        JSONObject r = (JSONObject)ranks.get(j);
                        for(Iterator iterator1 = com_vendors.iterator(); iterator1.hasNext();)
                        {
                            AnalyseVendor com_vendor = (AnalyseVendor)iterator1.next();
                            if(com_vendor.getOsName().equalsIgnoreCase(r.getString("name")))
                            {
                                int co = r.getInt("compare_count");
                                r.put("compare_count", Integer.valueOf(co + com_vendor.getArrivalCount().intValue()));
                            }
                        }

                    }

                    ComparatorJSON comparator = new ComparatorJSON();
                    Collections.sort(ranks, comparator);
                    obj.put("code", Integer.valueOf(0));
                    obj.put("datas", ranks);
                } else
                {
                    obj.put("code", Integer.valueOf(-2));
                }
            } else
            {
                obj.put("code", Integer.valueOf(-1));
            }
        }
        catch(Exception e)
        {
            obj.put("code", Integer.valueOf(-9));
            e.printStackTrace();
        }
        return obj.toString();
    }
}
