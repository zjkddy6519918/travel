package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * Servlet基类，用于分发请求
 */
public class BaseServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求路径
        String uri = req.getRequestURI();
        //获取方法名称
        String methodName = uri.substring(uri.lastIndexOf('/') + 1);
        try {
            //获取方法对象
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //method.setAccessible(true);
            //执行方法
            method.invoke(this, req,resp);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


    /**
     * 工具方法，用于返回结果对象到客户端
     * @param response
     * @param ri
     * @throws IOException
     */
    protected void sendResultInfo(HttpServletResponse response, ResultInfo ri) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), ri);
    }

    /**
     * 工具方法，用于返回自定义对象到客户端
     * @param response
     * @param o
     * @throws IOException
     */
    protected void sendObject(HttpServletResponse response, Object o) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), o);
    }

    /**
     * 具方法，用于返回序列化后的自定义对象
     * @param o
     * @return
     * @throws JsonProcessingException
     */
    protected String writeValueAsString(Object o) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(o);
    }
}
