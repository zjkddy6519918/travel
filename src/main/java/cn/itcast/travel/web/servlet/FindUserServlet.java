package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/findUserServlet")
public class FindUserServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json;charset=utf-8");

        User user = (User)request.getSession().getAttribute("user");
        ObjectMapper mapper = new ObjectMapper();
        ResultInfo ri = new ResultInfo();
        if (user == null){
            //尚未登录
            ri.setFlag(false);
        } else {
            ri.setFlag(true);
            ri.setData(user);
        }
        mapper.writeValue(response.getWriter(), ri);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
