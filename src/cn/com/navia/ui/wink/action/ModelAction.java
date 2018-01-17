// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ModelAction.java

package cn.com.navia.ui.wink.action;

import cn.com.navia.ui.service.AnalyseActiveDaoImpl;
import cn.com.navia.ui.utils.NaviaDateUtil;
import cn.com.navia.ui.wink.base.BaseAction;
import java.text.SimpleDateFormat;
import java.util.List;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.context.ApplicationContext;

public class ModelAction extends BaseAction
{

    public ModelAction()
    {
    }

    public String getHistoryCountList(String groupId, String startDate, String endDate)
    {
        JSONObject obj = new JSONObject();
        AnalyseActiveDaoImpl activeDao = (AnalyseActiveDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseActiveDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(groupId))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                List counts = activeDao.getHistoryCountListByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId));
                obj.put("code", Integer.valueOf(0));
                obj.put("dates", dates);
                obj.put("datas", counts);
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

    public String getCompareHistoryCountList(String groupId, String startDate, String endDate, String compareStartDate)
    {
        JSONObject obj = new JSONObject();
        AnalyseActiveDaoImpl activeDao = (AnalyseActiveDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseActiveDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(compareStartDate))
            {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                String compareEndDate = format2.format(DateUtils.addDays(format2.parse(compareStartDate), dates.size() - 1));
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                compareStartDate = (new StringBuilder(String.valueOf(compareStartDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                compareEndDate = (new StringBuilder(String.valueOf(compareEndDate))).append(" 23:59:59").toString();
                List counts = activeDao.getHistoryCountListByDate(format1.parse(startDate), format1.parse(endDate), Integer.parseInt(groupId));
                List com_counts = activeDao.getHistoryCountListByDate(format1.parse(compareStartDate), format1.parse(compareEndDate), Integer.parseInt(groupId));
                obj.put("code", Integer.valueOf(0));
                obj.put("dates", dates);
                obj.put("datas", counts);
                obj.put("compareDatas", com_counts);
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
