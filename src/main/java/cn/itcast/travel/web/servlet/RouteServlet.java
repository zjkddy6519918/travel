package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/route/*")
@SuppressWarnings("all")
public class RouteServlet extends BaseServlet {

    RouteService service = new RouteServiceImpl();

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");

        //1.接收参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");
        String rname = request.getParameter("rname");
        System.out.println("rname:"+rname);


        /*if (rname != null && rname.length() > 0){
            rname = new String(rname.getBytes("iso-8859-1"),"utf-8");
        }*/

        int cid = 0;
        int currentPage = 0;
        int pageSize = 0;

        //2.处理参数
        if (cidStr != null && cidStr.length() > 0){
            cid = Integer.parseInt(cidStr);
        }
        if (pageSizeStr != null && pageSizeStr.length() > 0){
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 5;
        }
        if (currentPageStr != null && currentPageStr.length() > 0){
            currentPage = Integer.parseInt(currentPageStr);
            if (currentPage <= 0){
                currentPage = 1;
            }
        } else {
            currentPage = 1;
        }

        System.out.println("currentPage:"+currentPage);
        PageBean<Route> pageBean = service.pageQuery(cid, currentPage, pageSize, rname);

        if (pageBean == null){
            return;
        }

        sendObject(response, pageBean);

    }

    public void bak(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
