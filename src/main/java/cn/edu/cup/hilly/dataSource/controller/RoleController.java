package cn.edu.cup.hilly.dataSource.controller;

import cn.edu.cup.hilly.dataSource.service.RoleService;
import cn.edu.cup.hilly.dataSource.model.Role;
import cn.edu.cup.hilly.dataSource.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping("/all")
    public RespBean getAllRoles(){
        List<Role> roles = roleService.selectAllRoles();
        return RespBean.ok("查询全部用户成功",roles);
    }
}
