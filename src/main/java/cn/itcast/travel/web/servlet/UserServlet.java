package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet(value = "/user/*")
@SuppressWarnings("all")
public class UserServlet extends BaseServlet {

    UserService service = new UserServiceImpl();

    /**
     * 注册Servlet
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* 处理响应乱码问题:字节流需getBytes("UTF-8") */
        response.setContentType("application/json;charset=UTF-8");

        String check = request.getParameter("check");
        String check_server = (String)request.getSession().getAttribute("CHECKCODE_SERVER");
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        if (check_server == null || !check_server.equalsIgnoreCase(check)){
            ResultInfo ri = new ResultInfo();
            ri.setFlag(false);
            ri.setErrorMsg("验证码错误");
            System.out.println(ri);
            sendResultInfo(response,ri);
            return;
        }


        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        boolean flag = service.register(user);
        ResultInfo resultInfo = new ResultInfo();
        if (flag){
            resultInfo.setFlag(true);
        } else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("注册失败!");
        }
        System.out.println(resultInfo);
        sendResultInfo(response, resultInfo);
    }

    /**
     * 登录Servlet
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置响应格式
        response.setContentType("application/json;charset=UTF-8");

        String check = request.getParameter("check");
        String check_server = (String)request.getSession().getAttribute("CHECKCODE_SERVER");
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        if (check_server == null || !check_server.equalsIgnoreCase(check)){
            ResultInfo ri = new ResultInfo();
            ri.setFlag(false);
            ri.setErrorMsg("验证码错误");
            System.out.println(ri);
            sendResultInfo(response,ri);
            return;
        }
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        User u = service.login(user);
        ResultInfo ri = new ResultInfo();
        if (u == null){
            //账号或者密码错误
            ri.setFlag(false);
            ri.setErrorMsg("账号或者密码错误");
        } else if (!"Y".equals(u.getStatus())){
            ri.setFlag(false);
            ri.setErrorMsg("您尚未激活，请激活");
        } else {
            ri.setFlag(true);
            request.getSession().setAttribute("user", u);
        }
        sendResultInfo(response, ri);
    }

    /**
     * 获取当前已登录的user对象
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        User user = (User)request.getSession().getAttribute("user");
        ResultInfo ri = new ResultInfo();
        if (user == null){
            //尚未登录
            ri.setFlag(false);
        } else {
            //已登录
            ri.setFlag(true);
            ri.setData(user);
        }
        sendResultInfo(response, ri);
    }

    /**
     * 退出登录
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();

        response.sendRedirect(request.getContextPath()+"/login.html");
    }

    /**
     * 激活用户
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void activate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        if (code != null){
            boolean flag = service.activate(code);

            String msg = null;
            if (flag){
                msg = "激活成功，请<a href='../login.html'>登录</a>";
            } else {
                msg = "激活失败，请联系管理员";
            }
            response.getWriter().write(msg);
        }
    }

    public void find(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("find方法被执行了");
    }



}
