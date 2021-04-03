package cn.edu.cup.hilly.dataSource.controller;

import cn.edu.cup.hilly.dataSource.Service.mongo.HillyProjectService;
import cn.edu.cup.hilly.dataSource.Service.mongo.HillyService;
import cn.edu.cup.hilly.dataSource.model.mongo.project.HillyProject;
import cn.edu.cup.hilly.dataSource.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class HillyProjectController {
    @Autowired
    HillyProjectService hillyProjectService;
    @Autowired
    HillyService hillyService;

    @GetMapping("/getAll")
    public RespBean getAll() {
        try {
            List<HillyProject> all = hillyProjectService.getAll();
            return RespBean.ok("查询成功",all);
        } catch (Exception e) {
            return RespBean.error("查询失败",e.getClass().getName());
        }
    }

    @GetMapping("/getById")
    public RespBean getById(@RequestParam("id") String id) {
        try {
            HillyProject byId = hillyProjectService.getById(id);
            return RespBean.ok("查询成功",byId);
        } catch (Exception e) {
            return RespBean.error("查询失败",e.getClass().getName());
        }
    }

    @PostMapping("/insert")
    public RespBean addProject(@RequestBody HillyProject hillyProject) {
        try {
            HillyProject add = hillyProjectService.add(hillyProject);
            if (add != null) {
                return RespBean.ok("添加成功",add);
            }
            return RespBean.error("添加失败");
        } catch (Exception e) {
            return RespBean.error("添加失败",e.getClass().getName());
        }

    }


}
