package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

import java.util.UUID;

public class UserServiceImpl implements UserService {
    UserDao dao = new UserDaoImpl();

    @Override
    public boolean register(User user) {
        User u = dao.findByUsername(user.getUsername());
        if (u != null){
            System.out.println("用户已存在");
            return false;
        }

        user.setCode(UuidUtil.getUuid());
        user.setStatus("N");
        dao.save(user);

        String content = "<a href='http://localhost/travel/activateUserServlet?code="+user.getCode()+"'>点击激活</a>";
        new Thread(() -> MailUtils.sendMail(user.getEmail(), content, "激活邮件")).start();
        return true;
    }

    @Override
    public boolean activate(String code) {
        User user = dao.findByCode(code);
        System.out.println("activating user:"+user);
        if (user == null){
            return false;
        }
        dao.updateStatus(user);
        return true;


    }

    @Override
    public User login(User user) {
        return  dao.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }
}
