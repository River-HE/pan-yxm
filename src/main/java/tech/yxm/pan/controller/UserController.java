package tech.yxm.pan.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.yxm.pan.pojo.User;
import tech.yxm.pan.service.UserService;

/**
 * @author river
 * @date 2020/11/17 16:11:36
 * @description
 */

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("login")
    public String loginByPassword(@RequestParam String username, @RequestParam String password) {
        if (userService.loginByPassword(username, password)) {
            return "登录成功";
        } else {
            return "登录失败";
        }
    }

    @PostMapping("registration")
    public String registration(@RequestBody User user) {
        if (userService.toRegister(user)) {
            return "注册成功";
        } else {
            return "注册失败";
        }
    }
}
