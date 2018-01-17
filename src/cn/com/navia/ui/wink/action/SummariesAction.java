// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SummariesAction.java

package cn.com.navia.ui.wink.action;

import cn.com.navia.ui.service.*;
import cn.com.navia.ui.utils.NaviaDateUtil;
import cn.com.navia.ui.wink.base.BaseAction;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.context.ApplicationContext;

public class SummariesAction extends BaseAction
{

    public SummariesAction()
    {
    }

    public String getSummariesStat(String groupId)
    {
        JSONObject obj = new JSONObject();
        AnalyseStayExtDaoImpl extDao = (AnalyseStayExtDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayExtDaoImpl);
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        AnalyseActiveDaoImpl activeDao = (AnalyseActiveDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseActiveDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(groupId))
            {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                Date today = format2.parse(format2.format(new Date()));
                Date yesterday = DateUtils.addDays(today, -1);
                String yesterdayStart = (new StringBuilder(String.valueOf(format2.format(yesterday)))).append(" 00:00:00").toString();
                String yesterdayEnd = (new StringBuilder(String.valueOf(format2.format(yesterday)))).append(" 23:59:59").toString();
                int indoor_count = stayDao.getIndoorCountByDate(format1.parse(yesterdayStart), format1.parse(yesterdayEnd), Integer.parseInt(groupId));
                int stay_count = extDao.getGt10StayCountByDate(format1.parse(yesterdayStart), format1.parse(yesterdayEnd), Integer.parseInt(groupId));
                int stay_conversion = stayDao.getStayGt10ConversionByDate(format1.parse(yesterdayStart), format1.parse(yesterdayEnd), Integer.parseInt(groupId));
                int history_count = activeDao.getHistoryCountByDate(yesterday, Integer.parseInt(groupId));
                int new_count = activeDao.getNewCustomCountByDate(format1.parse(yesterdayStart), format1.parse(yesterdayEnd), Integer.parseInt(groupId));
                int unrepeat_count = stayDao.getUnRepeatCountByDate(format1.parse(yesterdayStart), format1.parse(yesterdayEnd), Integer.parseInt(groupId));
                int new_conversion = 0;
                if(unrepeat_count == 0)
                    new_conversion = 0;
                else
                    new_conversion = (new_count * 100) / unrepeat_count;
                int active_count = activeDao.getActiveCountByDate(yesterday, Integer.parseInt(groupId));
                int cur_active = activeDao.getCurrentActiveCountByDate(format1.parse(yesterdayStart), format1.parse(yesterdayEnd), Integer.parseInt(groupId));
                int active_conversion = 0;
                if(unrepeat_count == 0)
                    active_conversion = 0;
                else
                    active_conversion = (cur_active * 100) / unrepeat_count;
                obj.put("code", Integer.valueOf(0));
                obj.put("indoor_count", Integer.valueOf(indoor_count));
                obj.put("stay_count", Integer.valueOf(stay_count));
                obj.put("stay_conversion", Integer.valueOf(stay_conversion));
                obj.put("history_count", Integer.valueOf(history_count));
                obj.put("new_count", Integer.valueOf(new_count));
                obj.put("new_conversion", Integer.valueOf(new_conversion));
                obj.put("active_count", Integer.valueOf(active_count));
                obj.put("active_conversion", Integer.valueOf(active_conversion));
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

    public String getTrafficChart(String groupId)
    {
        JSONObject obj = new JSONObject();
        AnalyseStayExtDaoImpl extDao = (AnalyseStayExtDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayExtDaoImpl);
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(groupId))
            {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                Date nowDate = format2.parse(format2.format(new Date()));
                Date yesterday = DateUtils.addDays(nowDate, -1);
                Date monthago = DateUtils.addDays(yesterday, -30);
                java.util.List dates = NaviaDateUtil.getDateList(format2.format(yesterday), format2.format(monthago));
                String startDate = (new StringBuilder(String.valueOf(format2.format(monthago)))).append(" 00:00:00").toString();
                String endDate = (new StringBuilder(String.valueOf(format2.format(yesterday)))).append(" 23:59:59").toString();
                java.util.List indoors = stayDao.getIndoorCountListByDate(format1.parse(startDate), format2.parse(endDate), Integer.parseInt(groupId));
                java.util.List stays = extDao.getGt10StayListByDate(format1.parse(startDate), format2.parse(endDate), Integer.parseInt(groupId));
                obj.put("code", Integer.valueOf(0));
                obj.put("dates", dates);
                obj.put("indoors", indoors);
                obj.put("stays", stays);
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
