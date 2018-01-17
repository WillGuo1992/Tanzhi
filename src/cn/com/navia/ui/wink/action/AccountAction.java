// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AccountAction.java

package cn.com.navia.ui.wink.action;

import cn.com.navia.ui.mybatis.model.*;
import cn.com.navia.ui.service.*;
import cn.com.navia.ui.wink.base.BaseAction;
import java.net.URLEncoder;
import java.util.List;
import javax.servlet.http.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;

public class AccountAction extends BaseAction
{

    public AccountAction()
    {
    }

    public String register(String username, String password, String telephonenum, String validatecode)
    {
        JSONObject obj = new JSONObject();
        ManagerDaoImpl managerDao = (ManagerDaoImpl)getCtx().getBean(ManagerDaoImpl.class);
        GroupDaoImpl groupDao = (GroupDaoImpl)getCtx().getBean(GroupDaoImpl.class);
        MallDaoImpl mallDao = (MallDaoImpl)getCtx().getBean(MallDaoImpl.class);
        log.info("username:{},password:{},telephone:{},validatecode:{}", new Object[] {
            username, password, telephonenum, validatecode
        });
        try
        {
            if(StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password) && StringUtils.isNotEmpty(telephonenum) && StringUtils.isNotEmpty(validatecode))
            {
                String randomCode = (String)request.getSession().getAttribute("RANDOMVALIDATECODEKEY");
                log.info("validate code ----------{}", randomCode);
                if(validatecode.equalsIgnoreCase(randomCode))
                {
                    List m1 = managerDao.getManagerByUserName(username);
                    List m2 = managerDao.getManagerByTelephone(telephonenum);
                    if(m1 != null && m1.size() > 0)
                    {
                        obj.put("code", Integer.valueOf(-3));
                        obj.put("desc", "\u8BE5\u7528\u6237\u540D\u5DF2\u88AB\u6CE8\u518C");
                    } else
                    if(m2 != null && m2.size() > 0)
                    {
                        obj.put("code", Integer.valueOf(-5));
                        obj.put("desc", "\u8BE5\u624B\u673A\u53F7\u7801\u5DF2\u88AB\u6CE8\u518C");
                    } else
                    {
                        int gid = groupDao.CreateGroup("", 0, 0, false, 0);
                        if(gid != 0)
                        {
                            int mid = mallDao.createMall(gid);
                            Mall mall = mallDao.getMallById(mid);
                            Manager m = new Manager();
                            m.setUsername(username);
                            m.setPassword(password);
                            m.setTelephone(telephonenum);
                            m.setLevel(Integer.valueOf(0));
                            m.setMallId(Integer.valueOf(mid));
                            int i = managerDao.createManager(m);
                            if(i != 0)
                            {
                                HttpSession session = request.getSession();
                                session.setAttribute("login_id", m.getId());
                                session.setAttribute("login_level", m.getLevel());
                                session.setAttribute("mall_name", mall.getName());
                                session.setAttribute("mall_id", mall.getId());
                                session.setAttribute("group_id", mall.getGroupId());
                                obj.put("code", Integer.valueOf(0));
                                obj.put("desc", "\u6CE8\u518C\u6210\u529F");
                            }
                        } else
                        {
                            obj.put("code", Integer.valueOf(-9));
                        }
                    }
                } else
                {
                    obj.put("code", Integer.valueOf(-4));
                    obj.put("desc", "\u9A8C\u8BC1\u7801\u9519\u8BEF");
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

    public String validateCode(String userinfo, String validatecode)
    {
        JSONObject obj = new JSONObject();
        ManagerDaoImpl managerDao = (ManagerDaoImpl)getCtx().getBean(ManagerDaoImpl.class);
        try
        {
            if(StringUtils.isNotEmpty(validatecode))
            {
                String randomCode = (String)request.getSession().getAttribute("RANDOMVALIDATECODEKEY");
                log.info("validate code ----------{}", randomCode);
                if(validatecode.equalsIgnoreCase(randomCode))
                {
                    List managers = managerDao.getManagerByLoginfo(userinfo);
                    if(managers != null && managers.size() > 0)
                    {
                        obj.put("code", Integer.valueOf(0));
                        obj.put("mid", ((Manager)managers.get(0)).getId());
                        obj.put("desc", "\u67E5\u8BE2\u6210\u529F");
                    } else
                    {
                        obj.put("code", Integer.valueOf(-2));
                        obj.put("desc", "\u672A\u67E5\u8BE2\u5230\u6570\u636E\u6216\u975E\u7BA1\u7406\u5458\u7528\u6237");
                    }
                } else
                {
                    obj.put("code", Integer.valueOf(-4));
                    obj.put("desc", "\u9A8C\u8BC1\u7801\u9519\u8BEF");
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

    public String login(String loginfo, String password, boolean iskeep)
    {
        JSONObject obj = new JSONObject();
        try
        {
            if(StringUtils.isNotEmpty(loginfo) && StringUtils.isNotEmpty(password))
            {
                HttpSession session = request.getSession();
                ManagerDaoImpl managerDao = (ManagerDaoImpl)getCtx().getBean(ManagerDaoImpl.class);
                MallDaoImpl mallDao = (MallDaoImpl)getCtx().getBean(MallDaoImpl.class);
                List managers = managerDao.getManagerByLoginfo(loginfo);
                if(managers != null && managers.size() > 0)
                {
                    Manager manager = (Manager)managers.get(0);
                    if(manager.getPassword().equals(password))
                    {
                        Mall mall = mallDao.getMallById(manager.getMallId().intValue());
                        obj.put("code", Integer.valueOf(0));
                        obj.put("desc", "\u767B\u5F55\u6210\u529F");
                        session.setAttribute("login_id", manager.getId());
                        session.setAttribute("login_level", manager.getLevel());
                        session.setAttribute("mall_name", mall.getName());
                        session.setAttribute("mall_id", mall.getId());
                        session.setAttribute("group_id", mall.getGroupId());
                        session.setAttribute("telephone", manager.getTelephone());
                        if(iskeep)
                        {
                            Cookie login_id_cookie = new Cookie("loginid", (new StringBuilder()).append(manager.getId()).toString());
                            Cookie login_level_cookie = new Cookie("loginlevel", (new StringBuilder()).append(manager.getLevel()).toString());
                            Cookie login_name_cookie = new Cookie("mallname", URLEncoder.encode(mall.getName(), "utf-8"));
                            Cookie mall_id_cookie = new Cookie("mallid", (new StringBuilder()).append(mall.getId()).toString());
                            Cookie group_id_cookie = new Cookie("groupid", (new StringBuilder()).append(mall.getGroupId()).toString());
                            Cookie telephone_cookie = new Cookie("telephone", manager.getTelephone());
                            login_id_cookie.setMaxAge(0x9450c00);
                            login_level_cookie.setMaxAge(0x9450c00);
                            login_name_cookie.setMaxAge(0x9450c00);
                            mall_id_cookie.setMaxAge(0x9450c00);
                            group_id_cookie.setMaxAge(0x9450c00);
                            telephone_cookie.setMaxAge(0x9450c00);
                            login_id_cookie.setPath("/");
                            login_level_cookie.setPath("/");
                            login_name_cookie.setPath("/");
                            mall_id_cookie.setPath("/");
                            group_id_cookie.setPath("/");
                            telephone_cookie.setPath("/");
                            response.addCookie(login_id_cookie);
                            response.addCookie(login_level_cookie);
                            response.addCookie(login_name_cookie);
                            response.addCookie(mall_id_cookie);
                            response.addCookie(group_id_cookie);
                            response.addCookie(telephone_cookie);
                        }
                    } else
                    {
                        obj.put("code", Integer.valueOf(-1));
                        obj.put("desc", "\u5BC6\u7801\u9519\u8BEF");
                    }
                } else
                {
                    UserDaoImpl userDao = (UserDaoImpl)getCtx().getBean(cn/com/navia/ui/service/UserDaoImpl);
                    List users = userDao.getUsersByloginfo(loginfo);
                    if(users != null && users.size() > 0)
                    {
                        User user = (User)users.get(0);
                        if(user.getPassword().equals(password))
                        {
                            Mall mall = mallDao.getMallById(user.getMallId().intValue());
                            obj.put("code", Integer.valueOf(0));
                            obj.put("desc", "\u767B\u5F55\u6210\u529F");
                            session.setAttribute("login_id", user.getId());
                            session.setAttribute("login_level", user.getLevel());
                            session.setAttribute("mall_name", mall.getName());
                            session.setAttribute("mall_id", mall.getId());
                            session.setAttribute("group_id", mall.getGroupId());
                            if(iskeep)
                            {
                                Cookie login_id_cookie = new Cookie("loginid", (new StringBuilder()).append(user.getId()).toString());
                                Cookie login_level_cookie = new Cookie("loginlevel", (new StringBuilder()).append(user.getLevel()).toString());
                                Cookie login_name_cookie = new Cookie("mallname", URLEncoder.encode(mall.getName(), "utf-8"));
                                Cookie mall_id_cookie = new Cookie("mallid", (new StringBuilder()).append(mall.getId()).toString());
                                Cookie group_id_cookie = new Cookie("groupid", (new StringBuilder()).append(mall.getGroupId()).toString());
                                login_id_cookie.setMaxAge(0x9450c00);
                                login_level_cookie.setMaxAge(0x9450c00);
                                login_name_cookie.setMaxAge(0x9450c00);
                                mall_id_cookie.setMaxAge(0x9450c00);
                                group_id_cookie.setMaxAge(0x9450c00);
                                login_id_cookie.setPath("/");
                                login_level_cookie.setPath("/");
                                login_name_cookie.setPath("/");
                                mall_id_cookie.setPath("/");
                                group_id_cookie.setPath("/");
                                response.addCookie(login_id_cookie);
                                response.addCookie(login_level_cookie);
                                response.addCookie(login_name_cookie);
                                response.addCookie(mall_id_cookie);
                                response.addCookie(group_id_cookie);
                            }
                        } else
                        {
                            obj.put("code", Integer.valueOf(-1));
                            obj.put("desc", "\u5BC6\u7801\u9519\u8BEF");
                        }
                    } else
                    {
                        obj.put("code", Integer.valueOf(-2));
                        obj.put("desc", "\u8BE5\u8D26\u6237\u4E0D\u5B58\u5728");
                    }
                }
            } else
            {
                obj.put("code", Integer.valueOf(-1));
                obj.put("desc", "\u7528\u6237\u540D\u6216\u5BC6\u7801\u4E0D\u80FD\u4E3A\u7A7A");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            obj.put("code", Integer.valueOf(-9));
        }
        return obj.toString();
    }

    public String logout()
        throws Exception
    {
        JSONObject obj = new JSONObject();
        HttpSession session = request.getSession();
        session.removeAttribute("login_id");
        session.removeAttribute("login_level");
        session.removeAttribute("mall_name");
        session.removeAttribute("mall_id");
        session.removeAttribute("group_id");
        session.removeAttribute("telephone");
        Cookie login_id_cookie = new Cookie("loginid", null);
        Cookie login_level_cookie = new Cookie("loginlevel", null);
        Cookie login_name_cookie = new Cookie("mallname", null);
        Cookie mall_id_cookie = new Cookie("mallid", null);
        Cookie group_id_cookie = new Cookie("groupid", null);
        Cookie telephone_cookie = new Cookie("telephone", null);
        login_id_cookie.setMaxAge(-1);
        login_level_cookie.setMaxAge(-1);
        login_name_cookie.setMaxAge(-1);
        mall_id_cookie.setMaxAge(-1);
        group_id_cookie.setMaxAge(-1);
        telephone_cookie.setMaxAge(-1);
        login_id_cookie.setPath("/");
        login_level_cookie.setPath("/");
        login_name_cookie.setPath("/");
        mall_id_cookie.setPath("/");
        group_id_cookie.setPath("/");
        telephone_cookie.setPath("/");
        response.addCookie(login_id_cookie);
        response.addCookie(login_level_cookie);
        response.addCookie(login_name_cookie);
        response.addCookie(mall_id_cookie);
        response.addCookie(group_id_cookie);
        response.addCookie(telephone_cookie);
        obj.put("code", Integer.valueOf(0));
        return obj.toString();
    }

    public String getManager(String managerid)
    {
        JSONObject obj = new JSONObject();
        ManagerDaoImpl managerDao = (ManagerDaoImpl)getCtx().getBean(cn/com/navia/ui/service/ManagerDaoImpl);
        try
        {
            Manager manager = managerDao.getManagerById(Integer.parseInt(managerid));
            if(manager != null)
            {
                obj.put("code", Integer.valueOf(0));
                obj.put("info", manager);
                obj.put("desc", "\u67E5\u8BE2\u6210\u529F");
            } else
            {
                obj.put("code", Integer.valueOf(-1));
                obj.put("desc", "\u672A\u67E5\u8BE2\u5230\u6570\u636E");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            obj.put("code", Integer.valueOf(-9));
            obj.put("desc", "\u670D\u52A1\u5668\u5F02\u5E38");
        }
        return obj.toString();
    }

    public String resetPwd(String managerid, String password)
    {
        JSONObject obj = new JSONObject();
        ManagerDaoImpl managerDao = (ManagerDaoImpl)getCtx().getBean(cn/com/navia/ui/service/ManagerDaoImpl);
        try
        {
            Manager manager = managerDao.getManagerById(Integer.parseInt(managerid));
            if(manager != null)
            {
                manager.setPassword(password);
                int i = managerDao.updateManager(manager);
                if(i != 0)
                    obj.put("code", Integer.valueOf(0));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            obj.put("code", Integer.valueOf(-9));
            obj.put("desc", "\u670D\u52A1\u5668\u5F02\u5E38");
        }
        return obj.toString();
    }

    public String updatePassword(String loginid, String expassword, String password, String level)
    {
        JSONObject obj = new JSONObject();
        ManagerDaoImpl managerDao = (ManagerDaoImpl)getCtx().getBean(cn/com/navia/ui/service/ManagerDaoImpl);
        UserDaoImpl userDao = (UserDaoImpl)getCtx().getBean(cn/com/navia/ui/service/UserDaoImpl);
        try
        {
            if(Integer.parseInt(level) == 0)
            {
                Manager manager = managerDao.getManagerById(Integer.parseInt(loginid));
                if(manager != null)
                    if(manager.getPassword().equals(expassword))
                    {
                        manager.setPassword(password);
                        int i = managerDao.updateManager(manager);
                        if(i != 0)
                            obj.put("code", Integer.valueOf(0));
                    } else
                    {
                        obj.put("code", Integer.valueOf(-1));
                        obj.put("desc", "\u539F\u5BC6\u7801\u9519\u8BEF");
                    }
            } else
            {
                User user = userDao.getUserById(Integer.parseInt(loginid));
                if(user != null)
                    if(user.getPassword().equals(expassword))
                    {
                        user.setPassword(password);
                        int i = userDao.updateUser(user);
                        if(i != 0)
                            obj.put("code", Integer.valueOf(0));
                    } else
                    {
                        obj.put("code", Integer.valueOf(-1));
                        obj.put("desc", "\u539F\u5BC6\u7801\u9519\u8BEF");
                    }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            obj.put("code", Integer.valueOf(-9));
            obj.put("desc", "\u670D\u52A1\u5668\u5F02\u5E38");
        }
        return obj.toString();
    }

    public String updateTelephone(String loginid, String telephone)
    {
        JSONObject obj = new JSONObject();
        ManagerDaoImpl managerDao = (ManagerDaoImpl)getCtx().getBean(cn/com/navia/ui/service/ManagerDaoImpl);
        try
        {
            Manager manager = managerDao.getManagerById(Integer.parseInt(loginid));
            if(manager != null)
            {
                manager.setTelephone(telephone);
                int i = managerDao.updateManager(manager);
                if(i != 0)
                {
                    HttpSession session = request.getSession();
                    session.setAttribute("telephone", telephone);
                    Cookie telephone_cookie = new Cookie("telephone", telephone);
                    telephone_cookie.setMaxAge(0x9450c00);
                    telephone_cookie.setPath("/");
                    response.addCookie(telephone_cookie);
                    obj.put("code", Integer.valueOf(0));
                    obj.put("info", telephone);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            obj.put("code", Integer.valueOf(-9));
            obj.put("desc", "\u670D\u52A1\u5668\u5F02\u5E38");
        }
        return obj.toString();
    }

    public String getUsers(String mallId)
    {
        JSONObject obj = new JSONObject();
        UserDaoImpl userDao = (UserDaoImpl)getCtx().getBean(cn/com/navia/ui/service/UserDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(mallId))
            {
                List users = userDao.getUsersByMallId(Integer.parseInt(mallId));
                obj.put("code", Integer.valueOf(0));
                obj.put("users", users);
            } else
            {
                obj.put("code", Integer.valueOf(-1));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            obj.put("code", Integer.valueOf(-9));
            obj.put("desc", "\u670D\u52A1\u5668\u5F02\u5E38");
        }
        return obj.toString();
    }

    public String addUser(String mallId, String username, String password, String telephone, String description)
    {
        JSONObject obj = new JSONObject();
        UserDaoImpl userDao = (UserDaoImpl)getCtx().getBean(cn/com/navia/ui/service/UserDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(mallId) && StringUtils.isNotEmpty(password) && StringUtils.isNotEmpty(username))
            {
                User u = new User();
                u.setMallId(Integer.valueOf(Integer.parseInt(mallId)));
                u.setUsername(username);
                u.setPassword(password);
                u.setTelephone(telephone);
                u.setDescription(description);
                u.setLevel(Integer.valueOf(1));
                int i = userDao.addUserRecord(u);
                if(i != 0)
                    obj.put("code", Integer.valueOf(0));
                else
                    obj.put("code", Integer.valueOf(-9));
            } else
            {
                obj.put("code", Integer.valueOf(-1));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            obj.put("code", Integer.valueOf(-9));
            obj.put("desc", "\u670D\u52A1\u5668\u5F02\u5E38");
        }
        return obj.toString();
    }

    public String getUserInfo(String userId)
    {
        JSONObject obj = new JSONObject();
        UserDaoImpl userDao = (UserDaoImpl)getCtx().getBean(cn/com/navia/ui/service/UserDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(userId))
            {
                User u = userDao.getUserById(Integer.parseInt(userId));
                obj.put("code", Integer.valueOf(0));
                obj.put("info", u);
            } else
            {
                obj.put("code", Integer.valueOf(-1));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            obj.put("code", Integer.valueOf(-9));
            obj.put("desc", "\u670D\u52A1\u5668\u5F02\u5E38");
        }
        return obj.toString();
    }

    public String editUser(String userId, String username, String password, String telephone, String description)
    {
        JSONObject obj = new JSONObject();
        UserDaoImpl userDao = (UserDaoImpl)getCtx().getBean(cn/com/navia/ui/service/UserDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(password) && StringUtils.isNotEmpty(username))
            {
                User u = userDao.getUserById(Integer.parseInt(userId));
                u.setUsername(username);
                u.setPassword(password);
                u.setTelephone(telephone);
                u.setDescription(description);
                int i = userDao.updateUser(u);
                if(i != 0)
                    obj.put("code", Integer.valueOf(0));
                else
                    obj.put("code", Integer.valueOf(-9));
            } else
            {
                obj.put("code", Integer.valueOf(-1));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            obj.put("code", Integer.valueOf(-9));
            obj.put("desc", "\u670D\u52A1\u5668\u5F02\u5E38");
        }
        return obj.toString();
    }

    public String deleteUser(String userId)
    {
        JSONObject obj = new JSONObject();
        UserDaoImpl userDao = (UserDaoImpl)getCtx().getBean(cn/com/navia/ui/service/UserDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(userId))
            {
                int i = userDao.deleteUser(Integer.parseInt(userId));
                if(i != 0)
                    obj.put("code", Integer.valueOf(0));
                else
                    obj.put("code", Integer.valueOf(-9));
            } else
            {
                obj.put("code", Integer.valueOf(-1));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            obj.put("code", Integer.valueOf(-9));
            obj.put("desc", "\u670D\u52A1\u5668\u5F02\u5E38");
        }
        return obj.toString();
    }
}
