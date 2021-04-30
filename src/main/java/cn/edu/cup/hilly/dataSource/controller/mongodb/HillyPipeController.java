package cn.edu.cup.hilly.dataSource.controller.mongodb;

import cn.edu.cup.hilly.dataSource.model.mongo.Pipe.Pipe;
import cn.edu.cup.hilly.dataSource.service.mongo.HillyPipeService;
import cn.edu.cup.hilly.dataSource.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wong
 * @date 2021年04月29日 14:27
 */
@RestController
@RequestMapping("/pipe")
public class HillyPipeController {

    @Autowired
    HillyPipeService hillyPipeService;

    /**
     * 根据项目id查询pipe
     * @param id
     * @return
     */
    @GetMapping("/getById")
    public RespBean getById(@RequestParam("id") String id) {
        try {
            Pipe pipe = hillyPipeService.getById(id);
            if (pipe != null) {
                return RespBean.ok("查询成功",pipe);
            }
            return RespBean.error("查询无结果");
        } catch (Exception e) {
            return RespBean.error("查询出错",e.getClass().getName());
        }
    }

    @PutMapping("/update")
    public RespBean update(@RequestParam("id") String id,@RequestBody Pipe pipe) {
        try {
            long update = hillyPipeService.update(id, pipe);
            if (update == 1) {
                return RespBean.ok("更新成功");
            }
            return RespBean.error("未更新数据");
        } catch (Exception e) {
            return RespBean.error("更新出错",e.getClass().getName());
        }
    }
}
