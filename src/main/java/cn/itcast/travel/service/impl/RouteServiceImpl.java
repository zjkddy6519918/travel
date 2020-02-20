package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {

    RouteDao dao = new RouteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {

        PageBean<Route> pageBean = new PageBean<>();

        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);

        int totalCount = dao.findTotalCount(cid,rname);
        if (totalCount == 0){
            System.out.println("暂无数据");
            return null;
        }

        pageBean.setTotalCount(totalCount);

        int totalPage = totalCount % pageSize == 0
                ?  totalCount / pageSize
                : totalCount / pageSize + 1;
        pageBean.setTotalPage(totalPage);

        List<Route> routes = dao.pageQuery(cid, (currentPage - 1) * pageSize, pageSize,rname);
        pageBean.setList(routes);

        return pageBean;
    }
}
