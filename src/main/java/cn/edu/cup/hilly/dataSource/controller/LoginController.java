package cn.edu.cup.hilly.dataSource.controller;

import cn.edu.cup.hilly.dataSource.mapper.UserMapper;
import cn.edu.cup.hilly.dataSource.model.User;
import cn.edu.cup.hilly.dataSource.service.UserService;
import cn.edu.cup.hilly.dataSource.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static cn.edu.cup.hilly.dataSource.utils.MD5.MD5Encode;

/**
 * @author CodeChap
 * @date 2021-07-24 9:36
 * @description LoginConfig
 */
@RestController
public class LoginController {

    @Autowired
    UserMapper userMapper;

    @GetMapping("/login")
    public RespBean login() {
        return RespBean.error("尚未登录，请登录！！！");
    }

    @PostMapping("/doLogin")
    public RespBean doLogin(@RequestParam String username, @RequestParam String password) {
        User user = userMapper.loadUserByUsername(username);
        boolean isCorrect = user.getPassword().equals(MD5Encode(password, "utf8"));
        if (isCorrect) {
            user.setPassword(null);
            return RespBean.ok("登陆成功",user);
        } else {
            return RespBean.ok("账号密码有问题");
        }
    }
}