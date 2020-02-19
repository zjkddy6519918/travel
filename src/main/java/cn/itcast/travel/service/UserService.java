package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    /**
     * 注册功能
     *
     * @param user
     * @return 注册是否成功
     */
    boolean register(User user);

    /**
     * 激活用户
     * @param user
     * @return 用户激活是否成功
     */
    boolean activate(String code);

    /**
     * 登录功能
     * @param user
     * @return
     */
    User login(User user);
}