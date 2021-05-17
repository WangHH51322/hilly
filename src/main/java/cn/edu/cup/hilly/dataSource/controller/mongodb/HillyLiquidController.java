package cn.edu.cup.hilly.dataSource.controller.mongodb;

import cn.edu.cup.hilly.dataSource.model.mongo.mediumList.Medium;
import cn.edu.cup.hilly.dataSource.model.mongo.mediumList.MediumList;
import cn.edu.cup.hilly.dataSource.service.mongo.HillyLiquidService;
import cn.edu.cup.hilly.dataSource.utils.RespBean;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mediumList")
public class HillyLiquidController {

    @Autowired
    HillyLiquidService hillyLiquidService;

    /**
     * 根据项目计算数据id获取全部流体介质
     * @param id
     * @return MediumList
     */
    @GetMapping("/getAll")
    public RespBean getMediumListById(@RequestParam("id") String id) {
        try {
            JSONObject mediumList = hillyLiquidService.getMediumListObjById(id);
//            List<MediumList> mediumList = hillyLiquidService.getMediumListByIdNewAll(id);
            return RespBean.ok("流体介质列表查询成功!",mediumList);
        } catch (Exception e) {
            return RespBean.error("查询失败",e.getClass().getName());
        }
    }

    @GetMapping("/getList")
    public RespBean getMediumList(@RequestParam("id") String id) {
        try {
            List<Medium> mediumList = hillyLiquidService.getMediumList(id);
            return RespBean.ok("流体介质列表查询成功!",mediumList);
        } catch (Exception e) {
            return RespBean.error("查询失败",e.getClass().getName());
        }
    }

    @GetMapping("/getList2")
    public RespBean getMediumList2(@RequestParam("id") String id) {
        try {
            MediumList mediumList = hillyLiquidService.getMediumListById2(id);
            return RespBean.ok("流体介质列表查询成功!",mediumList);
        } catch (Exception e) {
            return RespBean.error("查询失败",e.getClass().getName());
        }
    }

    /**
     * 根据项目计算数据id和介质id查询单一流体介质
     * @param hid
     * @param id
     * @return Medium
     */
    @GetMapping("/getById")
    public RespBean getMediumById(@RequestParam("hid") String hid, @RequestParam("id") String id) {
        try {
//            Medium mediumById = hillyLiquidService.getMediumById(hid, id);
            Medium mediumById = hillyLiquidService.getMediumByIdNew(hid, id);
            if (mediumById != null) {
                return RespBean.ok("流体介质查询成功",mediumById);
            }
            return RespBean.error("查询为空");
        } catch (Exception e) {
            return RespBean.error("查询失败",e.getClass().getName());
        }

    }

    @PostMapping("/add")
    public RespBean add (@RequestParam("id") String id) {
        try {
            MediumList mediumList = hillyLiquidService.add(id);
            return RespBean.ok("新建成功",mediumList);
        } catch (Exception e) {
            return RespBean.error("新建出错",e.getClass().getName());
        }
    }


    /**
     * 根据项目计算数据id添加单一流体介质
     * @param hid
     * @param medium
     * @return
     */
    @PostMapping("/insert")
    public RespBean insert(@RequestParam("hid") String hid, @RequestBody Medium medium) {
        try {
//            long modified = hillyLiquidService.insert(hid, medium);
            long modified = hillyLiquidService.insertNew(hid, medium);
            if (modified == 1) {
                return RespBean.ok("流体介质添加成功");
            }
            return RespBean.error("流体介质添加失败");
        } catch (Exception e) {
            return RespBean.error("流体介质添加出错",e.getClass().getName());
        }
    }

    /**
     * 根据项目计算数据id修改单一流体介质
     * @param hid
     * @param medium
     * @return
     */
    @PutMapping("/update")
    public RespBean update(@RequestParam("hid") String hid, @RequestBody Medium medium) {
        try {
            long update = hillyLiquidService.update(hid, medium);
            if (update == 1) {
                return RespBean.ok("流体介质更新成功");
            }
            return RespBean.error("流体介质更新失败");
        } catch (Exception e) {
            return RespBean.error("流体介质更新出错",e.getClass().getName());
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
            long delete = hillyLiquidService.delete(hid, id);
            if (delete == 1) {
                return RespBean.ok("流体介质删除成功");
            }
            return RespBean.error("流体介质删除失败");
        } catch (Exception e) {
            return RespBean.error("流体介质删除出错",e.getClass().getName());
        }
    }
}
