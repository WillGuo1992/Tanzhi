// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Client.java

package cn.com.navia.ui.utils;

import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client
{

    public Client(String sn, String password)
        throws UnsupportedEncodingException
    {
        serviceURL = "http://sdk105.entinfo.cn:8060/webservice.asmx";
        this.sn = "";
        this.password = "";
        pwd = "";
        this.sn = sn;
        this.password = password;
        pwd = getMD5((new StringBuilder(String.valueOf(sn))).append(password).toString());
    }

    public String getMD5(String sourceStr)
        throws UnsupportedEncodingException
    {
        String resultStr = "";
        try
        {
            byte temp[] = sourceStr.getBytes();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(temp);
            byte b[] = md5.digest();
            for(int i = 0; i < b.length; i++)
            {
                char digit[] = {
                    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
                    'A', 'B', 'C', 'D', 'E', 'F'
                };
                char ob[] = new char[2];
                ob[0] = digit[b[i] >>> 4 & 0xf];
                ob[1] = digit[b[i] & 0xf];
                resultStr = (new StringBuilder(String.valueOf(resultStr))).append(new String(ob)).toString();
            }

            return resultStr;
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public String register(String province, String city, String trade, String entname, String linkman, String phone, String mobile, 
            String email, String fax, String address, String postcode)
    {
        String result = "";
        String soapAction = "http://tempuri.org/Register";
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        xml = (new StringBuilder(String.valueOf(xml))).append("<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<soap12:Body>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<Register xmlns=\"http://tempuri.org/\">").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<sn>").append(sn).append("</sn>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<pwd>").append(password).append("</pwd>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<province>").append(province).append("</province>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<city>").append(city).append("</city>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<trade>").append(trade).append("</trade>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<entname>").append(entname).append("</entname>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<linkman>").append(linkman).append("</linkman>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<phone>").append(phone).append("</phone>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<mobile>").append(mobile).append("</mobile>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<email>").append(email).append("</email>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<fax>").append(fax).append("</fax>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<address>").append(address).append("</address>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<postcode>").append(postcode).append("</postcode>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<sign></sign>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("</Register>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("</soap12:Body>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("</soap12:Envelope>").toString();
        try
        {
            URL url = new URL(serviceURL);
            URLConnection connection = url.openConnection();
            HttpURLConnection httpconn = (HttpURLConnection)connection;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            bout.write(xml.getBytes());
            byte b[] = bout.toByteArray();
            httpconn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpconn.setRequestProperty("Content-Type", "text/xml; charset=gb2312");
            httpconn.setRequestProperty("SOAPAction", soapAction);
            httpconn.setRequestMethod("POST");
            httpconn.setDoInput(true);
            httpconn.setDoOutput(true);
            OutputStream out = httpconn.getOutputStream();
            out.write(b);
            out.close();
            InputStreamReader isr = new InputStreamReader(httpconn.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            String inputLine;
            while((inputLine = in.readLine()) != null) 
            {
                Pattern pattern = Pattern.compile("<RegisterResult>(.*)</RegisterResult>");
                for(Matcher matcher = pattern.matcher(inputLine); matcher.find();)
                    result = matcher.group(1);

            }
            in.close();
            return new String(result.getBytes(), "utf-8");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }

    public String chargeFee(String cardno, String cardpwd)
    {
        String result = "";
        String soapAction = "http://tempuri.org/ChargUp";
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        xml = (new StringBuilder(String.valueOf(xml))).append("<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<soap12:Body>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<ChargUp xmlns=\"http://tempuri.org/\">").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<sn>").append(sn).append("</sn>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<pwd>").append(password).append("</pwd>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<cardno>").append(cardno).append("</cardno>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<cardpwd>").append(cardpwd).append("</cardpwd>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("</ChargUp>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("</soap12:Body>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("</soap12:Envelope>").toString();
        try
        {
            URL url = new URL(serviceURL);
            URLConnection connection = url.openConnection();
            HttpURLConnection httpconn = (HttpURLConnection)connection;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            bout.write(xml.getBytes());
            byte b[] = bout.toByteArray();
            httpconn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpconn.setRequestProperty("Content-Type", "text/xml; charset=gb2312");
            httpconn.setRequestProperty("SOAPAction", soapAction);
            httpconn.setRequestMethod("POST");
            httpconn.setDoInput(true);
            httpconn.setDoOutput(true);
            OutputStream out = httpconn.getOutputStream();
            out.write(b);
            out.close();
            InputStreamReader isr = new InputStreamReader(httpconn.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            String inputLine;
            while((inputLine = in.readLine()) != null) 
            {
                Pattern pattern = Pattern.compile("<ChargUpResult>(.*)</ChargUpResult>");
                for(Matcher matcher = pattern.matcher(inputLine); matcher.find();)
                    result = matcher.group(1);

            }
            in.close();
            return new String(result.getBytes(), "utf-8");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }

    public String getBalance()
    {
        String result = "";
        String soapAction = "http://tempuri.org/balance";
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        xml = (new StringBuilder(String.valueOf(xml))).append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<soap:Body>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<balance xmlns=\"http://tempuri.org/\">").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<sn>").append(sn).append("</sn>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<pwd>").append(pwd).append("</pwd>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("</balance>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("</soap:Body>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("</soap:Envelope>").toString();
        try
        {
            URL url = new URL(serviceURL);
            URLConnection connection = url.openConnection();
            HttpURLConnection httpconn = (HttpURLConnection)connection;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            bout.write(xml.getBytes());
            byte b[] = bout.toByteArray();
            httpconn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpconn.setRequestProperty("Content-Type", "text/xml; charset=gb2312");
            httpconn.setRequestProperty("SOAPAction", soapAction);
            httpconn.setRequestMethod("POST");
            httpconn.setDoInput(true);
            httpconn.setDoOutput(true);
            OutputStream out = httpconn.getOutputStream();
            out.write(b);
            out.close();
            InputStreamReader isr = new InputStreamReader(httpconn.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            String inputLine;
            while((inputLine = in.readLine()) != null) 
            {
                Pattern pattern = Pattern.compile("<balanceResult>(.*)</balanceResult>");
                for(Matcher matcher = pattern.matcher(inputLine); matcher.find();)
                    result = matcher.group(1);

            }
            in.close();
            return new String(result.getBytes());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }

    public String mt(String mobile, String content, String ext, String stime, String rrid)
    {
        String result = "";
        String soapAction = "http://tempuri.org/mt";
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        xml = (new StringBuilder(String.valueOf(xml))).append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<soap:Body>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<mt xmlns=\"http://tempuri.org/\">").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<sn>").append(sn).append("</sn>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<pwd>").append(pwd).append("</pwd>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<mobile>").append(mobile).append("</mobile>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<content>").append(content).append("</content>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<ext>").append(ext).append("</ext>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<stime>").append(stime).append("</stime>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<rrid>").append(rrid).append("</rrid>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("</mt>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("</soap:Body>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("</soap:Envelope>").toString();
        try
        {
            URL url = new URL(serviceURL);
            URLConnection connection = url.openConnection();
            HttpURLConnection httpconn = (HttpURLConnection)connection;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            bout.write(xml.getBytes("utf-8"));
            byte b[] = bout.toByteArray();
            httpconn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpconn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            httpconn.setRequestProperty("SOAPAction", soapAction);
            httpconn.setRequestMethod("POST");
            httpconn.setDoInput(true);
            httpconn.setDoOutput(true);
            OutputStream out = httpconn.getOutputStream();
            out.write(b);
            out.close();
            InputStreamReader isr = new InputStreamReader(httpconn.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            String inputLine;
            while((inputLine = in.readLine()) != null) 
            {
                Pattern pattern = Pattern.compile("<mtResult>(.*)</mtResult>");
                for(Matcher matcher = pattern.matcher(inputLine); matcher.find();)
                    result = matcher.group(1);

            }
            return result;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }

    public String mo()
    {
        String result = "";
        String soapAction = "http://tempuri.org/mo";
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        xml = (new StringBuilder(String.valueOf(xml))).append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<soap:Body>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<mo xmlns=\"http://tempuri.org/\">").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<sn>").append(sn).append("</sn>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<pwd>").append(pwd).append("</pwd>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("</mo>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("</soap:Body>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("</soap:Envelope>").toString();
        try
        {
            URL url = new URL(serviceURL);
            URLConnection connection = url.openConnection();
            HttpURLConnection httpconn = (HttpURLConnection)connection;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            bout.write(xml.getBytes());
            byte b[] = bout.toByteArray();
            httpconn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpconn.setRequestProperty("Content-Type", "text/xml; charset=gb2312");
            httpconn.setRequestProperty("SOAPAction", soapAction);
            httpconn.setRequestMethod("POST");
            httpconn.setDoInput(true);
            httpconn.setDoOutput(true);
            OutputStream out = httpconn.getOutputStream();
            out.write(b);
            out.close();
            InputStream isr = httpconn.getInputStream();
            StringBuffer buff = new StringBuffer();
            byte byte_receive[] = new byte[10240];
            for(int i = 0; (i = isr.read(byte_receive)) != -1;)
                buff.append(new String(byte_receive, 0, i));

            isr.close();
            String result_before = buff.toString();
            int start = result_before.indexOf("<moResult>");
            int end = result_before.indexOf("</moResult>");
            result = result_before.substring(start + 10, end);
            return result;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }

    public String gxmt(String mobile, String content, String ext, String stime, String rrid)
    {
        String result = "";
        String soapAction = "http://tempuri.org/gxmt";
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        xml = (new StringBuilder(String.valueOf(xml))).append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<soap:Body>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<gxmt xmlns=\"http://tempuri.org/\">").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<sn>").append(sn).append("</sn>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<pwd>").append(pwd).append("</pwd>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<mobile>").append(mobile).append("</mobile>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<content>").append(content).append("</content>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<ext>").append(ext).append("</ext>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<stime>").append(stime).append("</stime>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<rrid>").append(rrid).append("</rrid>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("</gxmt>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("</soap:Body>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("</soap:Envelope>").toString();
        try
        {
            URL url = new URL(serviceURL);
            URLConnection connection = url.openConnection();
            HttpURLConnection httpconn = (HttpURLConnection)connection;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            bout.write(xml.getBytes());
            byte b[] = bout.toByteArray();
            httpconn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpconn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            httpconn.setRequestProperty("SOAPAction", soapAction);
            httpconn.setRequestMethod("POST");
            httpconn.setDoInput(true);
            httpconn.setDoOutput(true);
            OutputStream out = httpconn.getOutputStream();
            out.write(b);
            out.close();
            InputStreamReader isr = new InputStreamReader(httpconn.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            String inputLine;
            while((inputLine = in.readLine()) != null) 
            {
                Pattern pattern = Pattern.compile("<gxmtResult>(.*)</gxmtResult>");
                for(Matcher matcher = pattern.matcher(inputLine); matcher.find();)
                    result = matcher.group(1);

            }
            return result;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }

    public String UnRegister()
    {
        String result = "";
        String soapAction = "http://tempuri.org/UnRegister";
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        xml = (new StringBuilder(String.valueOf(xml))).append("<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<soap12:Body>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<UnRegister xmlns=\"http://tempuri.org/\">").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<sn>").append(sn).append("</sn>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<pwd>").append(password).append("</pwd>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("</UnRegister>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("</soap12:Body>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("</soap12:Envelope>").toString();
        try
        {
            URL url = new URL(serviceURL);
            URLConnection connection = url.openConnection();
            HttpURLConnection httpconn = (HttpURLConnection)connection;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            bout.write(xml.getBytes());
            byte b[] = bout.toByteArray();
            httpconn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpconn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            httpconn.setRequestProperty("SOAPAction", soapAction);
            httpconn.setRequestMethod("POST");
            httpconn.setDoInput(true);
            httpconn.setDoOutput(true);
            OutputStream out = httpconn.getOutputStream();
            out.write(b);
            out.close();
            InputStreamReader isr = new InputStreamReader(httpconn.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            String inputLine;
            while((inputLine = in.readLine()) != null) 
            {
                Pattern pattern = Pattern.compile("<UnRegisterResult>String</UnRegisterResult>");
                for(Matcher matcher = pattern.matcher(inputLine); matcher.find();)
                    result = matcher.group(1);

            }
            in.close();
            return new String(result.getBytes());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }

    public String UDPPwd(String newPwd)
    {
        String result = "";
        String soapAction = "http://tempuri.org/UDPPwd";
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        xml = (new StringBuilder(String.valueOf(xml))).append("<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<soap12:Body>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<UDPPwd  xmlns=\"http://tempuri.org/\">").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<sn>").append(sn).append("</sn>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<pwd>").append(password).append("</pwd>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<newpwd>").append(newPwd).append("</newpwd>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("</UDPPwd>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("</soap12:Body>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("</soap12:Envelope>").toString();
        try
        {
            URL url = new URL(serviceURL);
            URLConnection connection = url.openConnection();
            HttpURLConnection httpconn = (HttpURLConnection)connection;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            bout.write(xml.getBytes());
            byte b[] = bout.toByteArray();
            httpconn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpconn.setRequestProperty("Content-Type", "text/xml; charset=gb2312");
            httpconn.setRequestProperty("SOAPAction", soapAction);
            httpconn.setRequestMethod("POST");
            httpconn.setDoInput(true);
            httpconn.setDoOutput(true);
            OutputStream out = httpconn.getOutputStream();
            out.write(b);
            out.close();
            InputStreamReader isr = new InputStreamReader(httpconn.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            String inputLine;
            while((inputLine = in.readLine()) != null) 
            {
                Pattern pattern = Pattern.compile("<UDPPwdResult>(.*)</UDPPwdResult>");
                for(Matcher matcher = pattern.matcher(inputLine); matcher.find();)
                    result = matcher.group(1);

            }
            in.close();
            return new String(result.getBytes(), "utf-8");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }

    public String mdSmsSend_u(String mobile, String content, String ext, String stime, String rrid)
    {
        String result = "";
        String soapAction = "http://tempuri.org/mdSmsSend_u";
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        xml = (new StringBuilder(String.valueOf(xml))).append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<soap:Body>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<mdSmsSend_u xmlns=\"http://tempuri.org/\">").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<sn>").append(sn).append("</sn>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<pwd>").append(pwd).append("</pwd>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<mobile>").append(mobile).append("</mobile>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<content>").append(content).append("</content>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<ext>").append(ext).append("</ext>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<stime>").append(stime).append("</stime>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("<rrid>").append(rrid).append("</rrid>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("</mdSmsSend_u>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("</soap:Body>").toString();
        xml = (new StringBuilder(String.valueOf(xml))).append("</soap:Envelope>").toString();
        try
        {
            URL url = new URL(serviceURL);
            URLConnection connection = url.openConnection();
            HttpURLConnection httpconn = (HttpURLConnection)connection;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            bout.write(xml.getBytes("GBK"));
            byte b[] = bout.toByteArray();
            httpconn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpconn.setRequestProperty("Content-Type", "text/xml; charset=gb2312");
            httpconn.setRequestProperty("SOAPAction", soapAction);
            httpconn.setRequestMethod("POST");
            httpconn.setDoInput(true);
            httpconn.setDoOutput(true);
            OutputStream out = httpconn.getOutputStream();
            out.write(b);
            out.close();
            InputStreamReader isr = new InputStreamReader(httpconn.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            String inputLine;
            while((inputLine = in.readLine()) != null) 
            {
                Pattern pattern = Pattern.compile("<mdSmsSend_uResult>(.*)</mdSmsSend_uResult>");
                for(Matcher matcher = pattern.matcher(inputLine); matcher.find();)
                    result = matcher.group(1);

            }
            return result;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }

    private String serviceURL;
    private String sn;
    private String password;
    private String pwd;
}
