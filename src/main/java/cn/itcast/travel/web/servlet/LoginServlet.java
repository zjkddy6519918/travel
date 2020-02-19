package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet(value = "/loginServlet")
public class LoginServlet extends HttpServlet {

    @SuppressWarnings("all")
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        UserService service = new UserServiceImpl();
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void sendResultInfo(HttpServletResponse response, ResultInfo ri) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), ri);
    }
}
