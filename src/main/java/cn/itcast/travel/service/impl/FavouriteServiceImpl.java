package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavouriteDao;
import cn.itcast.travel.dao.impl.FavouriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavouriteService;

public class FavouriteServiceImpl implements FavouriteService {

    FavouriteDao dao = new FavouriteDaoImpl();

    @Override
    public boolean isFavourite(int rid, int uid) {
        Favorite f = dao.findByRidAndUid(rid, uid);
        if (f == null){
            return false;
        }
        return true;
    }

    @Override
    public void add(int rid, int uid) {
        dao.add(rid, uid);
    }
}
