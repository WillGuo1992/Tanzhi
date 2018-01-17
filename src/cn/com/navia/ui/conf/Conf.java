// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Conf.java

package cn.com.navia.ui.conf;

import cn.com.navia.ui.utils.XMLUtils;
import java.io.File;
import java.io.PrintStream;
import java.net.URI;
import javax.xml.bind.JAXBException;

public class Conf
{

    public Conf()
    {
    }

    public static void refresh(String confBaseURI)
    {
        File xmlFile = null;
        try
        {
            xmlFile = new File(new URI((new StringBuilder(String.valueOf(confBaseURI))).append("/conf.xml").toString()));
            instance = (Conf)XMLUtils.parseXml(xmlFile, cn.com.navia.ui.conf.Conf.class);
        }
        catch(JAXBException e)
        {
            System.err.printf("%s: \u89E3\u6790\u51FA\u9519!\n", new Object[] {
                xmlFile.toURI()
            });
            e.printStackTrace();
        }
        catch(Exception e)
        {
            System.err.println("\u8BBE\u7F6E\u865A\u62DF\u673A\u53C2\u6570: -DconfPath=<confPath>");
            e.printStackTrace();
            System.exit(100);
        }
    }

    public static Conf getInstance(String baseURI)
    {
        confBaseURI = baseURI;
        refresh(baseURI);
        return instance;
    }

    public String getAnalyseFileURI()
    {
        return analyseFileURI;
    }

    public static Conf getInstance()
    {
        return instance;
    }

    public static String getConfBaseURI()
    {
        return confBaseURI;
    }

    private static Conf instance;
    private static String confBaseURI;
    private String analyseFileURI;
}
