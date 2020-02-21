package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavouriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class FavouriteDaoImpl implements FavouriteDao {

    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Favorite findByRidAndUid(int rid, int uid) {
        String sql = "select * from tab_favorite where rid = ? and uid = ?";
        Favorite favorite = null;
        try {
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<>(Favorite.class),rid,uid);
        } catch (Exception e){

        }
        return favorite;
    }

    @Override
    public int findCountByRid(int rid) {
        String sql = "select count(*) from tab_favorite where rid=?";
        int count = 0;
        try {
            count = template.queryForObject(sql, Integer.class, rid);
        } catch (Exception e){

        }
        return count;
    }

    @Override
    public void add(int rid, int uid) {
        String sql = "insert into tab_favorite values (?,?,?)";
        template.update(sql, rid,new Date(),uid);
    }
}
