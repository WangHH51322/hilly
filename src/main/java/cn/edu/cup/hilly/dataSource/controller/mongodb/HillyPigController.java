package cn.edu.cup.hilly.dataSource.controller.mongodb;

import cn.edu.cup.hilly.dataSource.model.mongo.pigList.Pig;
import cn.edu.cup.hilly.dataSource.model.mongo.pigList.PigList;
import cn.edu.cup.hilly.dataSource.service.mongo.HillyPigService;
import cn.edu.cup.hilly.dataSource.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wong
 * @date 2021年04月29日 21:40
 */
@RestController
@RequestMapping("/pigList")
public class HillyPigController {

    @Autowired
    HillyPigService pigService;

    /**
     * 根据项目id获取全部的pigList列表
     * @param id
     * @return
     */
    @GetMapping("/getAll")
    public RespBean getAll(@RequestParam("id") String id) {
        try {
            PigList pigList = pigService.getAll(id);
            return RespBean.ok("查询成功",pigList);
        } catch (Exception e) {
            return RespBean.error("查询出错",e.getClass().getName());
        }
    }

    /**
     * 根据项目id和清管器id查询pig
     * @param hid
     * @param id
     * @return
     */
    @GetMapping("/getById")
    public RespBean getById(@RequestParam("hid") String hid,@RequestParam("id") String id) {
        try {
            Pig pig = pigService.getById(hid, id);
            if (pig != null) {
                return RespBean.ok("查询成功",pig);
            }
            return RespBean.error("未查询到数据");
        } catch (Exception e) {
            return RespBean.error("查询失败",e.getClass().getName());
        }
    }

    /**
     * 根据项目id,向pigList中添加单个pig
     * @param id
     * @param pig
     * @return
     */
    @PostMapping("/insert")
    public RespBean insert(@RequestParam("id") String id,@RequestBody Pig pig) {
        try {
            long insert = pigService.insert(id, pig);
            if (insert == 1) {
                return RespBean.ok("添加成功");
            }
            return RespBean.error("未添加进入");
        } catch (Exception e) {
            return RespBean.error("添加失败",e.getClass().getName());
        }
    }

    /**
     * 根据项目id和新pig更新旧pig
     * @param id
     * @param pig
     * @return
     */
    @PutMapping("/update")
    public RespBean update(@RequestParam("id") String id,@RequestBody Pig pig) {
        try {
            long update = pigService.update(id, pig);
            if (update == 1) {
                return RespBean.ok("更新成功");
            }
            return RespBean.error("未更新数据");
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
    public RespBean delete(@RequestParam("hid") String hid,@RequestParam("id") String id) {
        try {
            long delete = pigService.delete(hid, id);
            if (delete == 1) {
                return RespBean.ok("删除成功");
            }
            return RespBean.error("未删除数据");
        } catch (Exception e) {
            return RespBean.error("删除出错",e.getClass().getName());
        }
    }
}
