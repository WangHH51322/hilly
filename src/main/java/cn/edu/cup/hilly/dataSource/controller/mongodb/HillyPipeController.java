package cn.edu.cup.hilly.dataSource.controller.mongodb;

import cn.edu.cup.hilly.dataSource.model.mongo.Pipe.Pipe;
import cn.edu.cup.hilly.dataSource.model.mongo.mediumList.Medium;
import cn.edu.cup.hilly.dataSource.service.mongo.HillyPipeService;
import cn.edu.cup.hilly.dataSource.utils.RespBean;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wong
 * @date 2021年04月29日 14:27
 */
@RestController
@RequestMapping("/pipeList")
public class HillyPipeController {

    @Autowired
    HillyPipeService hillyPipeService;

    /**
     * 根据项目id查询pipeList
     * @param id
     * @return
     */
    @GetMapping("/getAll")
    public RespBean getAll(@RequestParam("id") String id) {
        try {
            JSONObject pipe = hillyPipeService.getAll(id);
            if (pipe != null) {
                return RespBean.ok("查询成功",pipe);
            }
            return RespBean.error("查询无结果");
        } catch (Exception e) {
            return RespBean.error("查询出错",e.getClass().getName());
        }
    }

    /**
     * 根据项目计算数据id和介质id查询单一管道
     * @param hid
     * @param id
     * @return Medium
     */
    @GetMapping("/getById")
    public RespBean getPipe(@RequestParam("hid") String hid, @RequestParam("id") String id) {
        try {
            Pipe pipeById = hillyPipeService.getPipeById(hid, id);
            if (pipeById != null) {
                return RespBean.ok("管段查询成功",pipeById);
            }
            return RespBean.error("查询为空");
        } catch (Exception e) {
            return RespBean.error("查询失败",e.getMessage());
        }

    }
    /**
     * 根据项目计算数据id添加单一管道
     * @param id
     * @param pipe
     * @return
     */
    @PostMapping("/insert")
    public RespBean insert(@RequestParam("id") String id, @RequestBody Pipe pipe) {
        try {
//            long modified = hillyLiquidService.insert(id, medium);
            Long insert = hillyPipeService.insert(id, pipe);
            if (insert == 1) {
                return RespBean.ok("管段添加成功");
            }
            return RespBean.error("管段添加失败");
        } catch (Exception e) {
            return RespBean.error("管段添加出错",e.getClass().getName());
        }
    }

    /**
     * 根据项目计算数据id修改单一管段
     * @param id
     * @param pipe
     * @return
     */
    @PutMapping("/update")
    public RespBean update(@RequestParam("id") String id, @RequestBody Pipe pipe) {
        try {
            long update = hillyPipeService.update(id, pipe);
            if (update == 1) {
                return RespBean.ok("管段更新成功");
            }
            return RespBean.error("管段更新失败");
        } catch (Exception e) {
            return RespBean.error("管段更新出错",e.getClass().getName());
        }
    }

    /**
     * 根据项目计算数据id删除单一流体介质
     * @param hid
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public RespBean delete(@RequestParam("hid") String hid, @RequestParam("id") String id) {
        try {
//            long delete = hillyLiquidService.delete(hid, id);
            long delete = hillyPipeService.delete(hid, id);
            if (delete == 1) {
                return RespBean.ok("管段删除成功");
            }
            return RespBean.error("管段删除失败");
        } catch (Exception e) {
            return RespBean.error("管段删除出错",e.getClass().getName());
        }
    }

}
