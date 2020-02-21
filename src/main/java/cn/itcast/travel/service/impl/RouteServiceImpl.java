package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavouriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImageDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavouriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImageDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {

    RouteDao routeDao = new RouteDaoImpl();
    RouteImageDao routeImageDao = new RouteImageDaoImpl();
    SellerDao sellerDao = new SellerDaoImpl();
    FavouriteDao favouriteDao = new FavouriteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {

        PageBean<Route> pageBean = new PageBean<>();

        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);

        int totalCount = routeDao.findTotalCount(cid,rname);
        if (totalCount == 0){
            System.out.println("暂无数据");
            return null;
        }

        pageBean.setTotalCount(totalCount);

        int totalPage = totalCount % pageSize == 0
                ?  totalCount / pageSize
                : totalCount / pageSize + 1;
        pageBean.setTotalPage(totalPage);

        List<Route> routes = routeDao.pageQuery(cid, (currentPage - 1) * pageSize, pageSize,rname);
        pageBean.setList(routes);

        return pageBean;
    }

    @Override
    public Route findOne(int rid) {

        Route route = routeDao.findOne(rid);
        List<RouteImg> imageList = routeImageDao.findById(rid);
        route.setRouteImgList(imageList);
        Seller seller = sellerDao.findById(route.getSid());
        route.setSeller(seller);
        int count = favouriteDao.findCountByRid(rid);
        route.setCount(count);
        return route;
    }
}
