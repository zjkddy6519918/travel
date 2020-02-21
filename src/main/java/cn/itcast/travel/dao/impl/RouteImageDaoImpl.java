package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteImageDao;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImageDaoImpl implements RouteImageDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<RouteImg> findById(int rid) {
        String sql = "select * from tab_route_img where rid=?";
        List<RouteImg> list = null;
        try{
            list = template.query(sql, new BeanPropertyRowMapper<>(RouteImg.class), rid);
        }catch (Exception e){

        }
        return list;
    }
}
