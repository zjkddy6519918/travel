package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavouriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavouriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/route/*")
@SuppressWarnings("all")
public class RouteServlet extends BaseServlet {

    private RouteService service = new RouteServiceImpl();
    private FavouriteService fService = new FavouriteServiceImpl();

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

    /**
     * 根据rid获取旅游路线的详细信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ridStr = request.getParameter("rid");
        int rid = 0;
        if (ridStr != null && ridStr.length() > 0){
            rid = Integer.parseInt(ridStr);
        }
        Route route = service.findOne(rid);
        sendObject(response, route);
    }

    /**
     * 判断用户是否将指定路线收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavourite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User)request.getSession().getAttribute("user");
        ResultInfo resultInfo = new ResultInfo();
        if (user == null){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户尚未登录");
            resultInfo.setData(1);
            sendResultInfo(response, resultInfo);
            return;
        }

        int rid = 0;
        String ridStr = request.getParameter("rid");
        if (ridStr != null && ridStr.length() > 0){
            rid = Integer.parseInt(ridStr);
        }

        boolean isFavourite = fService.isFavourite(rid, user.getUid());
        if (!isFavourite){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("未加入到收藏");
            resultInfo.setData(2);
        } else {
            resultInfo.setFlag(true);
        }
        sendResultInfo(response, resultInfo);
    }

    /**
     * 添加到收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavourite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int rid = 0;
        String ridStr = request.getParameter("rid");
        if (ridStr != null && ridStr.length() > 0){
            rid = Integer.parseInt(ridStr);
        }
        //此时默认用户已经登录，因为在isFavourite中已做过判断
        User user = (User)request.getSession().getAttribute("user");
        int uid = user.getUid();

        fService.add(rid, uid);
        sendObject(response, true);
    }
}
