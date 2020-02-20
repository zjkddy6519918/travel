package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {

    CategoryDao dao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        List<Category> resultList = null;
        //保存redis服务器状态
        boolean flag;
        Jedis jedis = null;
        try {
            //访问redis缓存
            jedis = JedisUtil.getJedis();
            Set<String> category = jedis.zrange("category", 0, -1);
            if (category == null || category.size() == 0){
                //redis中无数据信息
                flag = true;
            } else {
                System.out.println("通过Redis访问菜单信息");
                resultList = new ArrayList<Category>();
                int index = 0;
                for (String s : category){
                    index ++;
                    Category c = new Category();
                    c.setCid(index);
                    c.setCname(s);
                    resultList.add(c);
                }
                return resultList;
            }
        } catch (Exception e){
            //redis服务器产生异常
            flag = false;
        }
        //无法通过Redis访问菜单信息
        System.out.println("通过MySQL访问菜单信息");
        resultList = dao.findAll();
        if (flag){
            //保存至redis服务器
            for (Category c : resultList){
                jedis.zadd("category", c.getCid(), c.getCname());
            }
        }
        return resultList;
    }
}
