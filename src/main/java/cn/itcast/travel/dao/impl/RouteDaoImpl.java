package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<Route> pageQuery(int cid, int start, int pageSize, String rname) {
        String sql = "select * from tab_route where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if (cid != 0){
            sb.append(" and cid=? ");
            params.add(cid);
        }
        if (rname != null && rname.length() > 0){
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        sb.append(" limit ?,? ");
        params.add(start);
        params.add(pageSize);

        sql = sb.toString();
        System.out.println(sql);
        List<Route> query = null;
        try {
            query = template.query(sql, new BeanPropertyRowMapper<>(Route.class), params.toArray());
        } catch (Exception e){

        }
        return query;
    }

    @Override
    public int findTotalCount(int cid, String rname) {
        String sql = "select count(*) from tab_route where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);

        List params = new ArrayList();

        if (cid != 0){
            params.add(cid);
            sb.append(" and cid = ? ");
        }
        System.out.println("rname:"+rname);
        if (rname != null && rname.length() > 0){
            params.add("%"+rname+"%");
            sb.append(" and rname like ? ");
        }
        sql = sb.toString();
        System.out.println(sql);
        Integer rs = null;
        try {
            rs = template.queryForObject(sql, Integer.class, params.toArray());
        } catch (Exception e){
            rs = 0;
        }
        return rs;
    }
}
