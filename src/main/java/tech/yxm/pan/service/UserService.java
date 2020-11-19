package tech.yxm.pan.service;

import tech.yxm.pan.pojo.User;

/**
 * @author river
 * @date 2020/11/17 16:14:34
 * @description
 */

public interface UserService {

    /**
     * 通过密码登录
     *
     * @param username 用户名
     * @param password 密码
     * @return
     * @throws Exception
     */
    boolean loginByPassword(String username, String password);

    /**
     * 注册
     *
     * @param user 用户实体
     * @return
     */
    boolean toRegister(User user);
}
