// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NaviaDateUtil.java

package cn.com.navia.ui.utils;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.lang3.time.DateUtils;

public class NaviaDateUtil
{

    public NaviaDateUtil()
    {
    }

    public static List getDateList(String startDateStr, String endDateStr)
        throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse(startDateStr);
        Date endDate = sdf.parse(endDateStr);
        List dates = new ArrayList();
        if(startDate.equals(endDate))
            dates.add(startDateStr);
        else
        if(startDate.before(endDate))
        {
            while(startDate.before(endDate)) 
            {
                dates.add(startDateStr);
                startDate = DateUtils.addDays(startDate, 1);
                startDateStr = sdf.format(startDate);
            }
            dates.add(endDateStr);
        } else
        {
            while(endDate.before(startDate)) 
            {
                dates.add(endDateStr);
                endDate = DateUtils.addDays(endDate, 1);
                endDateStr = sdf.format(endDate);
            }
            dates.add(startDateStr);
        }
        return dates;
    }

    public static List getWeekList(String startDate, String endDate)
        throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List list = new ArrayList();
        Date indexDate = sdf.parse(startDate);
        Calendar c = Calendar.getInstance();
        c.setTime(indexDate);
        for(; !indexDate.after(sdf.parse(getLastDayOfWeek(endDate))); indexDate = c.getTime())
        {
            list.add((new StringBuilder(String.valueOf(getFirstDayOfWeek(sdf.format(indexDate))))).append("~").append(getLastDayOfWeek(sdf.format(indexDate))).toString());
            System.out.println((new StringBuilder(String.valueOf(sdf.format(indexDate)))).append("------").append(getLastDayOfWeek(endDate)).toString());
            c.add(5, 7);
        }

        return list;
    }

    public static List getMonthList(String startDate, String endDate)
        throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List list = new ArrayList();
        Date indexDate = sdf.parse(startDate);
        Calendar c = Calendar.getInstance();
        c.setTime(indexDate);
        for(; !indexDate.after(sdf.parse(getLastDayOfMonth(endDate))); indexDate = c.getTime())
        {
            list.add((new StringBuilder(String.valueOf(getFirstDayOfMonth(sdf.format(indexDate))))).append("~").append(getLastDayOfMonth(sdf.format(indexDate))).toString());
            c.add(2, 1);
        }

        return list;
    }

    public static int getDayCounts(Date startDate, Date endDate)
        throws Exception
    {
        List dates = new ArrayList();
        if(startDate.equals(endDate))
            dates.add(startDate);
        else
        if(startDate.before(endDate))
        {
            for(; startDate.before(endDate); startDate = DateUtils.addDays(startDate, 1))
                dates.add(startDate);

            dates.add(endDate);
        } else
        {
            for(; endDate.before(startDate); endDate = DateUtils.addDays(endDate, 1))
                dates.add(endDate);

            dates.add(startDate);
        }
        return dates.size();
    }

    public static List getCharacterDatesByType(String startDate, String endDate, int type)
        throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List dates = new ArrayList();
        Date indexDate = sdf.parse(startDate);
        Calendar cal = Calendar.getInstance();
        for(; indexDate.compareTo(sdf.parse(endDate)) <= 0; indexDate = DateUtils.addDays(indexDate, 1))
        {
            cal.setTime(indexDate);
            if(cal.get(7) == type % 7 + 1)
            {
                System.out.println((new StringBuilder("--->")).append(cal.get(7)).toString());
                dates.add(sdf.format(indexDate));
            }
        }

        return dates;
    }

    public static String getFirstDayOfMonth(String date)
        throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(date));
        c.set(5, 1);
        return sdf.format(c.getTime());
    }

    public static String getLastDayOfMonth(String date)
        throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(date));
        c.add(2, 1);
        c.set(5, 1);
        c.add(5, -1);
        return sdf.format(c.getTime());
    }

    public static String getFirstDayOfWeek(String date)
        throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(date));
        int day_of_week = c.get(7) - 1;
        if(day_of_week == 0)
            day_of_week = 7;
        c.add(5, -day_of_week + 1);
        return sdf.format(c.getTime());
    }

    public static String getLastDayOfWeek(String date)
        throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(date));
        int day_of_week = c.get(7) - 1;
        if(day_of_week == 0)
            day_of_week = 7;
        c.add(5, -day_of_week + 7);
        return sdf.format(c.getTime());
    }

    public static String getFirstDayOfYear(String date)
        throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(date));
        c.set(6, 1);
        return sdf.format(c.getTime());
    }

    public static String getLastDayOfYear(String date)
        throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(date));
        c.add(1, 1);
        c.set(6, 1);
        c.add(5, -1);
        return sdf.format(c.getTime());
    }

    public static Date getLastWeekStartDate(String date)
        throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(date));
        c.add(5, -7);
        c.set(7, 2);
        return c.getTime();
    }

    public static Date getLastWeekEndDate(String date)
        throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(date));
        c.add(5, -7);
        c.add(4, 1);
        c.set(7, 1);
        return c.getTime();
    }

    public static Date getLastMonthStartDate(String date)
        throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(date));
        c.add(2, -1);
        c.set(5, 1);
        return c.getTime();
    }

    public static Date getLastMonthEndDate(String date)
        throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(date));
        c.set(5, 1);
        c.add(5, -1);
        return c.getTime();
    }

    public static int getHoursFromStarttime(String starttime)
        throws Exception
    {
        int hours = 0;
        if(starttime.indexOf(":") > 0)
        {
            int s_h = Integer.parseInt(starttime.split(":")[0]);
            hours = (new Date()).getHours() - s_h;
        }
        if(hours < 0)
            hours = 0;
        return hours;
    }

    public static void main(String args[])
    {
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            List counts = getWeekList("2014-09-28", "2014-10-30");
            System.out.println(counts);
            System.out.println(getLastDayOfWeek("2014-10-30"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
