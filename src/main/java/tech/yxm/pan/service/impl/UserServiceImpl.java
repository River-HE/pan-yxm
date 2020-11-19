package tech.yxm.pan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.yxm.pan.dao.UserDao;
import tech.yxm.pan.pojo.User;
import tech.yxm.pan.service.UserService;

/**
 * @author river
 * @date 2020/11/17 16:15:12
 * @description
 */

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean loginByPassword(String username, String password) {
        User user = userDao.findById(username).orElse(null);
        if (null == user) {
            return false;
        }
        if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean toRegister(User user) {
        if (null != userDao.findById(user.getUsername())) {
            return false;
        }
        userDao.save(user);
        return true;
    }
}
