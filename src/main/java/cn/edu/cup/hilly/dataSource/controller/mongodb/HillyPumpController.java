package cn.edu.cup.hilly.dataSource.controller.mongodb;

import cn.edu.cup.hilly.dataSource.model.mongo.pumpList.Pump;
import cn.edu.cup.hilly.dataSource.model.mongo.stationList.StationPumps;
import cn.edu.cup.hilly.dataSource.service.mongo.HillyStationPumpsService;
import cn.edu.cup.hilly.dataSource.utils.RespBean;
import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author CodeChap
 * @date 2021-05-16 22:36
 */

@RestController
@RequestMapping("/pumpList")
public class HillyPumpController {

    @Autowired
    HillyStationPumpsService hspService;

    @PostMapping("/add")
    public RespBean add(@RequestParam("id") String id) {
        StationPumps stationPumps = hspService.add(id);
        return RespBean.ok("添加成功",stationPumps);
    }

    @GetMapping("/all")
    public RespBean getAll(@RequestParam("id") String id) {
        BasicDBObject stationPumps = hspService.getAll(id);
        return RespBean.ok("查询成功",stationPumps);
    }

    @GetMapping("/one")
    public RespBean getOne(@RequestParam("hid") String hid,@RequestParam("pid") String pid) {
        Pump pump = hspService.getOne(hid, pid);
        return RespBean.ok("查询成功",pump);
    }

    @PostMapping("/insert")
    public RespBean insertOne(@RequestParam("id") String id,@RequestBody Pump pump) {
        Long insert = hspService.insert(id, pump);
        if (insert == 1) {
            return RespBean.ok("添加成功");
        }
        return RespBean.error("添加失败");
    }
}
