package com.kemp.test.dao;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.junit.Test;

public class UserDaoTest {

    @Test
    public void testDruidPath(){
        String path = JDBCUtils.class.getClassLoader().getResource("druid.properties").getPath();
        System.out.println(path);
    }

    @Test
    public void testFindUserByUsername(){
        UserDao dao = new UserDaoImpl();
        User test = dao.findByUsername("test");
        System.out.println(test);
    }
}
