package cn.itcast.travel.service;

public interface FavouriteService {

    boolean isFavourite(int rid, int uid);

    void add(int rid, int uid);
}
