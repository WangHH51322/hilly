package cn.edu.cup.hilly.dataSource.controller.mongodb;

import cn.edu.cup.hilly.dataSource.model.mongo.pigList.Pig;
import cn.edu.cup.hilly.dataSource.model.mongo.pigList.PigList;
import cn.edu.cup.hilly.dataSource.model.mongo.pumpList.Pump;
import cn.edu.cup.hilly.dataSource.model.mongo.stationList.Station;
import cn.edu.cup.hilly.dataSource.model.mongo.stationList.StationList;
import cn.edu.cup.hilly.dataSource.service.mongo.HillyStationService;
import cn.edu.cup.hilly.dataSource.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wong
 * @date 2021年04月30日 17:16
 */
@RestController
@RequestMapping("/stationList")
public class HillyStationController {

    @Autowired
    HillyStationService stationService;

    /**
     * 根据项目id查询全部站点信息
     * @param id
     * @return
     */
    @GetMapping("/getAll")
    public RespBean getAll(@RequestParam("id") String id) {
        try {
            StationList all = stationService.getAll(id);
            return RespBean.ok("查询成功",all);
        } catch (Exception e) {
            return RespBean.error("查询出错",e.getClass().getName());
        }
    }

    /**
     * 根据项目id和station的id查询station
     * @param hid
     * @param id
     * @return
     */
    @GetMapping("/getById")
    public RespBean getById(@RequestParam("hid") String hid,@RequestParam("id") String id) {
        try {
            Station station = stationService.getById(hid, id);
            if (station != null) {
                return RespBean.ok("查询成功",station);
            }
            return RespBean.error("未查询到结果");
        } catch (Exception e) {
            return RespBean.error("查询出错",e.getClass().getName());
        }
    }

    /**
     * 根据项目id和新建station,添加station
     * @param id
     * @param station
     * @return
     */
    @PostMapping("/insert")
    public RespBean insert(@RequestParam("id") String id,@RequestBody Station station) {
        try {
            long insert = stationService.insert(id, station);
            if (insert == 1) {
                return RespBean.ok("添加成功");
            }
            return RespBean.error("未添加数据");
        } catch (Exception e) {
            return RespBean.error("添加出错",e.getClass().getName());
        }
    }

    /**
     * 根据项目id 和 station基础新信息更新station
     * @param id
     * @param station
     * @return
     */
    @PutMapping("/basicInfo/update")
    public RespBean bIUpdate(@RequestParam("id") String id,@RequestBody Station station) {
        try {
            long bIUpdate = stationService.bIUpdate(id, station);
            if (bIUpdate == 1) {
                return RespBean.ok("更新成功");
            }
            return RespBean.error("未更新数据");
        } catch (Exception e) {
            return RespBean.error("更新出错",e.getClass().getName());
        }
    }

    /**
     * 根据项目id和站点id获取全部的站内泵列表
     * @param id
     * @return
     */
    @GetMapping("/stationPumps/getAll")
    public RespBean sPGetAll(@RequestParam("hid") String hid,@RequestParam("id") String id) {
        try {
            List<Pump> pumps = stationService.sPGetAll(hid, id);
            return RespBean.ok("查询成功",pumps);
        } catch (Exception e) {
            return RespBean.error("查询出错",e.getClass().getName());
        }
    }

    /**
     * 根据项目id,站点id向站内泵列表中添加单个pump
     * @param id
     * @param hid
     * @param pump
     * @return
     */
    @PostMapping("/stationPumps/insert")
    public RespBean sPInsert(@RequestParam("hid") String hid,@RequestParam("id") String id,@RequestBody Pump pump) {
        try {
            long sPInsert = stationService.sPInsert(hid, id, pump);
            if (sPInsert == 1) {
                return RespBean.ok("添加成功");
            }
            return RespBean.error("未添加数据");
        } catch (Exception e) {
            return RespBean.error("添加失败",e.getClass().getName());
        }
    }

    /**
     * 根据项目id和站点id,pump更新旧pump
     * @param id
     * @param hid
     * @param pump
     * @return
     */
    @PutMapping("/stationPumps/update")
    public RespBean sPUpdate(@RequestParam("hid") String hid,@RequestParam("id") String id,@RequestBody Pump pump) {
        try {
            long update = stationService.sPUpdate(hid,id,pump);
            if (update == 1) {
                return RespBean.ok("更新成功");
            }
            return RespBean.error("未更新数据");
        } catch (Exception e) {
            return RespBean.error("更新出错",e.getClass().getName());
        }
    }

    /**
     * 根据项目id和站点id,以及pump的id删除pump
     * @param hid
     * @param id
     * @param pid
     * @return
     */
    @DeleteMapping("/stationPumps/delete")
    public RespBean sPDelete(@RequestParam("hid") String hid,@RequestParam("id") String id,@RequestParam("pid") String pid) {
        try {
            long delete = stationService.sPDelete(hid, id, pid);
            if (delete == 1) {
                return RespBean.ok("删除成功");
            }
            return RespBean.error("未删除数据");
        } catch (Exception e) {
            return RespBean.error("删除出错",e.getClass().getName());
        }
    }


    @DeleteMapping("/delete")
    public RespBean delete(@RequestParam("hid") String hid,@RequestParam("id") String id) {
        try {
            long delete = stationService.delete(hid, id);
            if (delete == 1) {
                return RespBean.ok("删除成功");
            }
            return RespBean.error("未删除数据");
        } catch (Exception e) {
            return RespBean.error("删除出错",e.getClass().getName());
        }
    }
}
