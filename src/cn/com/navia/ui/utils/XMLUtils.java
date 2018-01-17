// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   XMLUtils.java

package cn.com.navia.ui.utils;

import java.io.File;
import java.io.Reader;
import javax.xml.bind.*;

public class XMLUtils
{

    public XMLUtils()
    {
    }

    public static Object parseXml(File file, Class clazz)
        throws JAXBException
    {
        Object t = null;
        JAXBContext context = JAXBContext.newInstance(new Class[] {
            clazz
        });
        Unmarshaller shaller = context.createUnmarshaller();
        t = shaller.unmarshal(file);
        return t;
    }

    public static Object parseXml(Reader reader, Class clazz)
        throws JAXBException
    {
        Object t = null;
        JAXBContext context = JAXBContext.newInstance(new Class[] {
            clazz
        });
        Unmarshaller shaller = context.createUnmarshaller();
        t = shaller.unmarshal(reader);
        return t;
    }
}
