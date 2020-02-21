package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {

    List<Route> pageQuery(int cid, int start, int pageSize, String rname);

    int findTotalCount(int cid, String rname);

    Route findOne(int rid);
}
