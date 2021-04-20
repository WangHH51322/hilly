package cn.edu.cup.hilly.dataSource.controller.mongodb;

import cn.edu.cup.hilly.dataSource.service.mongo.HillyProjectService;
import cn.edu.cup.hilly.dataSource.service.mongo.HillyService;
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

    /**
     * 查询全部的项目列表
     * @return List<HillyProject>
     */
    @GetMapping("/getAll")
    public RespBean getAll() {
        System.out.println("执行了查询方法");
        try {
            List<HillyProject> all = hillyProjectService.getAll();
            return RespBean.ok("查询成功",all);
        } catch (Exception e) {
            return RespBean.error("查询失败",e.getClass().getName());
        }
    }

    /**
     * 根据id查询单条项目信息
     * @param id
     * @return HillyProject
     */
    @GetMapping("/getById")
    public RespBean getById(@RequestParam("id") String id) {
        try {
            HillyProject byId = hillyProjectService.getById(id);
            return RespBean.ok("查询成功",byId);
        } catch (Exception e) {
            return RespBean.error("查询失败",e.getClass().getName());
        }
    }

    /**
     * 插入一条项目信息
     * @param hillyProject
     * @return
     */
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

    /**
     * 修改项目信息
     * @param hillyProject
     * @return
     */
    @PutMapping("/update")
    public RespBean updateProject(@RequestBody HillyProject hillyProject) {
        try {
            long update = hillyProjectService.update(hillyProject);
            if (update == 1) {
                return RespBean.ok("修改成功");
            }
            return RespBean.error("未进行任何修改");
        } catch (Exception e) {
            return RespBean.error("修改失败",e.getClass().getName());
        }
    }

    @DeleteMapping("/delete")
    public RespBean deleteProject(@RequestParam("id") String id) {
        try {
            long delete = hillyProjectService.delete(id);
            if (delete == 1) {
                return RespBean.ok("删除成功");
            }
            return RespBean.error("未做任何删除");
        } catch (Exception e) {
            return RespBean.error("删除失败",e.getClass().getName());
        }

    }
}
