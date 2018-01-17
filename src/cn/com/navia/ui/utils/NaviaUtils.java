// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NaviaUtils.java

package cn.com.navia.ui.utils;

import java.util.ArrayList;
import java.util.List;

public class NaviaUtils
{

    public NaviaUtils()
    {
    }

    public static List getRateListByNumList(List nums)
        throws Exception
    {
        List rates = new ArrayList();
        int sum = 0;
        if(nums != null && nums.size() > 0)
        {
            for(int i = 0; i < nums.size(); i++)
                sum += ((Integer)nums.get(i)).intValue();

            if(sum != 0)
            {
                for(int j = 0; j < nums.size(); j++)
                    rates.add(Double.valueOf((double)((Integer)nums.get(j)).intValue() / (double)sum));

            }
        }
        return rates;
    }

    public static List getResidenceRateListByNumList(List nums)
        throws Exception
    {
        List rates = new ArrayList();
        int ten = 0;
        int twenty = 0;
        int thirty = 0;
        int forty = 0;
        int fifty = 0;
        int sixty = 0;
        int more = 0;
        if(nums != null && nums.size() > 0)
        {
            List list = new ArrayList();
            for(int i = 0; i < nums.size(); i++)
            {
                int r = ((Integer)nums.get(i)).intValue();
                if(r > 0 && r <= 10)
                    ten++;
                else
                if(r > 10 && r <= 20)
                    twenty++;
                else
                if(r > 20 && r <= 30)
                    thirty++;
                else
                if(r > 30 && r <= 40)
                    forty++;
                else
                if(r > 40 && r <= 50)
                    fifty++;
                else
                if(r > 50 && r <= 60)
                    sixty++;
                else
                if(r > 60)
                    more++;
            }

            list.add(Integer.valueOf(ten));
            list.add(Integer.valueOf(twenty));
            list.add(Integer.valueOf(thirty));
            list.add(Integer.valueOf(forty));
            list.add(Integer.valueOf(fifty));
            list.add(Integer.valueOf(sixty));
            list.add(Integer.valueOf(more));
            rates = getRateListByNumList(list);
        }
        return rates;
    }
}
