package cn.edu.cup.hilly.dataSource.controller;

import cn.edu.cup.hilly.dataSource.utils.RespBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CodeChap
 * @date 2021-07-24 9:36
 * @description LoginConfig
 */
@RestController
public class LoginController {

    @GetMapping("/login")
    public RespBean login() {
        return RespBean.error("尚未登录，请登录！！！");
    }
}