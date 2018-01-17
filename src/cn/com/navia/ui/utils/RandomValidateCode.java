// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RandomValidateCode.java

package cn.com.navia.ui.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.http.*;

public class RandomValidateCode
{

    public RandomValidateCode()
    {
        random = new Random();
        randString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        width = 70;
        height = 40;
        lineSize = 10;
        stringNum = 4;
    }

    private Font getFont()
    {
        return new Font("Fixedsys", 1, 20);
    }

    private Color getRandColor(int fc, int bc)
    {
        if(fc > 255)
            fc = 255;
        if(bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc - 16);
        int g = fc + random.nextInt(bc - fc - 14);
        int b = fc + random.nextInt(bc - fc - 18);
        return new Color(r, g, b);
    }

    public void getRandcode(HttpServletRequest request, HttpServletResponse response)
    {
        HttpSession session = request.getSession();
        BufferedImage image = new BufferedImage(width, height, 4);
        Graphics g = image.getGraphics();
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", 0, 20));
        g.setColor(getRandColor(110, 133));
        for(int i = 0; i <= lineSize; i++)
            drowLine(g);

        String randomString = "";
        for(int i = 1; i <= stringNum; i++)
            randomString = drowString(g, randomString, i);

        session.removeAttribute("RANDOMVALIDATECODEKEY");
        session.setAttribute("RANDOMVALIDATECODEKEY", randomString);
        g.dispose();
        try
        {
            ImageIO.write(image, "JPEG", response.getOutputStream());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private String drowString(Graphics g, String randomString, int i)
    {
        g.setFont(getFont());
        g.setColor(new Color(random.nextInt(101), random.nextInt(111), random.nextInt(121)));
        String rand = String.valueOf(getRandomString(random.nextInt(randString.length())));
        randomString = (new StringBuilder(String.valueOf(randomString))).append(rand).toString();
        g.translate(random.nextInt(3), random.nextInt(3));
        g.drawString(rand, 13 * i, 16);
        return randomString;
    }

    private void drowLine(Graphics g)
    {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(13);
        int yl = random.nextInt(15);
        g.drawLine(x, y, x + xl, y + yl);
    }

    public String getRandomString(int num)
    {
        return String.valueOf(randString.charAt(num));
    }

    public static final String RANDOMCODEKEY = "RANDOMVALIDATECODEKEY";
    private Random random;
    private String randString;
    private int width;
    private int height;
    private int lineSize;
    private int stringNum;
}
