package cn.edu.cup.hilly.dataSource.controller.mongodb;

import cn.edu.cup.hilly.dataSource.model.mongo.mediumList.Medium;
import cn.edu.cup.hilly.dataSource.model.mongo.valveList.Valve;
import cn.edu.cup.hilly.dataSource.model.mongo.valveList.ValveList;
import cn.edu.cup.hilly.dataSource.service.mongo.HillyValveService;
import cn.edu.cup.hilly.dataSource.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/valveList")
public class HillyValveController {

    @Autowired
    HillyValveService valveService;

    /**
     * 查询全部valves
     * @param id
     * @return
     */
    @GetMapping("/getAll")
    public RespBean getAll(@RequestParam("id") String id) {
        try {
            ValveList all = valveService.getAll(id);
            return RespBean.ok("查询成功",all);
        } catch (Exception e) {
            return RespBean.error("查询出错",e.getClass().getName());
        }
    }

    /**
     * 根据valve的id进行查询
     * @param hid
     * @param id
     * @return
     */
    @GetMapping("getById")
    public RespBean getById(@RequestParam("hid") String hid, @RequestParam("id") String id) {
        try {
            Valve valve = valveService.getById(hid, id);
            if (valve != null) {
                return RespBean.ok("查询成功",valve);
            }
            return RespBean.error("未查询到结果");
        } catch (Exception e) {
            return RespBean.error("查询出错",e.getClass().getName());
        }
    }

    /**
     * 根据项目数据id添加阀门
     * @param id
     * @param valve
     * @return
     */
    @PostMapping("/insert")
    public RespBean add(@RequestParam("id") String id,@RequestBody Valve valve) {
        try {
            long add = valveService.add(id, valve);
            if (add == 1) {
                return RespBean.ok("阀添加成功");
            }
            return RespBean.error("未添加阀门");
        } catch (Exception e) {
            return RespBean.error("阀门添加出错",e.getClass().getName());
        }
    }

    /**
     * 根据项目id和pig对象,更新pig对象
     * @param id
     * @param valve
     * @return
     */
    @PutMapping("/update")
    public RespBean update(@RequestParam("id") String id,@RequestBody Valve valve) {
        try {
            long update = valveService.update(id, valve);
            if (update == 1) {
                return RespBean.ok("更新成功");
            }
            return RespBean.error("未更新任何数据");
        } catch (Exception e) {
            return RespBean.error("更新出错",e.getClass().getName());
        }
    }

    /**
     * 根据项目id和pig的id,删除对应的pig
     * @param hid
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public RespBean delete(@RequestParam("hid") String hid, @RequestParam("id") String id) {
        try {
            long delete = valveService.delete(hid, id);
            if (delete == 1) {
                return RespBean.ok("删除成功");
            }
            return RespBean.error("未删除数据");
        } catch (Exception e) {
            return RespBean.error("删除出错", e.getClass().getName());
        }
    }
}
