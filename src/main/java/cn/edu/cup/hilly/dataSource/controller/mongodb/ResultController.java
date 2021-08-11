package cn.edu.cup.hilly.dataSource.controller.mongodb;

import cn.edu.cup.hilly.dataSource.model.mongo.result.*;
import cn.edu.cup.hilly.dataSource.service.mongo.result.*;
import cn.edu.cup.hilly.dataSource.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author CodeChap
 * @date 2021-05-22 10:57
 */

@RestController
@RequestMapping("/result")
public class ResultController {

    @Autowired
    ResultDPLService resultDPLService;
    @Autowired
    ResultDHLService resultDHLService;
    @Autowired
    ResultHSSService resultHSSService;
    @Autowired
    ResultALFPService resultALFPService;
    @Autowired
    ResultLgHisService resultLgHisService;
    @Autowired
    ResultPgHisService resultPgHisService;
    @Autowired
    ResultMHisService resultMHisService;
    @Autowired
    ResultSimpleService resultSimpleService;
    @Autowired
    ResultULService resultULService;
    @Autowired
    ResultKeyPoints resultKeyPoints;

    @GetMapping("/DPL")
    public RespBean getDPL(@RequestParam("id") String id) {
        try {
            ResultDPL resultDPL = resultDPLService.find(id);
            return RespBean.ok("查询成功",resultDPL);
        } catch (Exception e) {
            return RespBean.error("查询出错",e.getMessage());
        }
    }
    @GetMapping("/DPL/Last")
    public RespBean getDPLLast(@RequestParam("id") String id) {
        try {
            ResultDPL resultDPL = resultDPLService.findLast(id);
            return RespBean.ok("查询成功",resultDPL);
        } catch (Exception e) {
            return RespBean.error("查询出错",e.getMessage());
        }
    }

    @GetMapping("/DHL")
    public RespBean getDHL(@RequestParam("id") String id) {
        try {
            Map<String,Object> resultDHL = resultDHLService.find(id);
            return RespBean.ok("查询成功",resultDHL);
        } catch (Exception e) {
            return RespBean.error("查询出错",e.getMessage());
        }
    }

    @GetMapping("/DHL/Last")
    public RespBean getDHLLats(@RequestParam("id") String id) {
        try {
            ResultDHL resultDHL = resultDHLService.findLast(id);
            return RespBean.ok("查询成功",resultDHL);
        } catch (Exception e) {
            return RespBean.error("查询出错",e.getMessage());
        }
    }

    @GetMapping("/HSS")
    public RespBean getHSS(@RequestParam("id") String id) {
        try {
            ResultHSS resultHSS = resultHSSService.find(id);
            return RespBean.ok("查询成功",resultHSS);
        } catch (Exception e) {
            return RespBean.error("查询出错",e.getMessage());
        }
    }

    @GetMapping("/ALFP")
    public RespBean getALFP (@RequestParam("id") String id) {
        try {
            ResultAllLineFP resultAllLineFP = resultALFPService.find(id);
            return RespBean.ok("查询成功",resultAllLineFP);
        } catch (Exception e) {
            return RespBean.error("查询出错",e.getMessage());
        }
    }
    @GetMapping("/ALFP2")
    public RespBean getALFP2 (@RequestParam("id") String id) {
        try {
            Map<Double, List<ResultAllLineFP.outPut>> resultAllLineFP = resultALFPService.find2(id);
            return RespBean.ok("查询成功",resultAllLineFP);
        } catch (Exception e) {
            return RespBean.error("查询出错",e.getMessage());
        }
    }

    @GetMapping("/ALFP/Last")
    public RespBean getALFPLast (@RequestParam("id") String id) {
        try {
            Map<Double, List<ResultAllLineFP.outPut>> resultAllLineFP = resultALFPService.findLast(id);
            return RespBean.ok("查询成功",resultAllLineFP);
        } catch (Exception e) {
            return RespBean.error("查询出错",e.getMessage());
        }
    }

    @GetMapping("/LgHis")
    public RespBean getLgHis (@RequestParam("id") String id) {
        try {
            ResultLgHis resultLgHis = resultLgHisService.find(id);
            return RespBean.ok("查询成功",resultLgHis);
        } catch (Exception e) {
            return RespBean.error("查询出错",e.getMessage());
        }
    }

    @GetMapping("/PgHis")
    public RespBean getPgHis (@RequestParam("id") String id) {
        try {
            ResultPgHis resultPgHis = resultPgHisService.find(id);
            return RespBean.ok("查询成功",resultPgHis);
        } catch (Exception e) {
            return RespBean.error("查询出错",e.getMessage());
        }
    }

    @GetMapping("/MHis")
    public RespBean getMHis (@RequestParam("id") String id) {
        try {
            ResultMHis resultMHis = resultMHisService.find(id);
            return RespBean.ok("查询成功",resultMHis);
        } catch (Exception e) {
            return RespBean.error("查询出错",e.getMessage());
        }
    }

    @GetMapping("/Simple")
    public RespBean getSimple (@RequestParam("id") String id) {
        // try {
            ResultSimple resultSimple = resultSimpleService.find(id);
            return RespBean.ok("查询成功",resultSimple);
        // } catch (Exception e) {
        //     return RespBean.error("查询出错",e.getMessage());
        // }
    }

    @GetMapping("/Simple/pigV")
    public RespBean getSimplePigV (@RequestParam("id") String id) {
        try {
            HashMap<String, List<double[]>> pigV = resultSimpleService.findPigV(id);
            return RespBean.ok("查询成功", pigV);
        } catch (Exception e) {
            return RespBean.error("查询出错",e.getMessage());
        }
    }

    @GetMapping("/Simple/pigL")
    public RespBean getSimplePigL (@RequestParam("id") String id) {
        try {
            HashMap<String, List<double[]>> pigL = resultSimpleService.findPigL(id);
            return RespBean.ok("查询成功", pigL);
        } catch (Exception e) {
            return RespBean.error("查询出错",e.getMessage());
        }
    }

    @GetMapping("/ULocation")
    public RespBean getULocation (@RequestParam("id") String id) {
        try {
            ResultULocation resultULocation = resultULService.find(id);
            return RespBean.ok("查询成功",resultULocation);
        } catch (Exception e) {
            return RespBean.error("查询出错",e.getMessage());
        }
    }

    @GetMapping("/KeyPoints/Data")
    public RespBean getKeyPointsData(@RequestParam("id") String id) {
//        try {
            List<KeyPoint> keyPoints = resultKeyPoints.getAllData(id);
            return RespBean.ok("查询成功",keyPoints);
//        } catch (Exception e) {
//            return RespBean.error("查询出错",e.getMessage());
//        }
    }

    @GetMapping("/KeyPoints")
    public RespBean getKeyPoints(@RequestParam("id") String id) {
        try {
            List<KeyPoint> keyPoints = resultKeyPoints.getAll(id);
            return RespBean.ok("查询成功",keyPoints);
        } catch (Exception e) {
            return RespBean.error("查询出错",e.getMessage());
        }
    }

    @PostMapping("/KeyPoints")
    public RespBean insertKeyPoint(@RequestParam("id") String id, @RequestBody KeyPoint keyPoint) {
        try {
            keyPoint.setProjectId(id);
            KeyPoint key = resultKeyPoints.insert(keyPoint);
            return RespBean.ok("添加成功",key);
        } catch (Exception e) {
            return RespBean.error("添加出错",e.getMessage());
        }
    }

    @PutMapping("/KeyPoints")
    public RespBean updateKeyPoint(@RequestBody KeyPoint keyPoints) {
        try {
            KeyPoint key = resultKeyPoints.update(keyPoints);
            return RespBean.ok("修改成功",key);
        } catch (Exception e) {
            return RespBean.error("修改出错",e.getMessage());
        }
    }

    @PutMapping("/KeyPoints/All")
    public RespBean updateKeyPointAll(@RequestBody List<KeyPoint> keyPoint) {
        try {
            KeyPoint key = resultKeyPoints.updateAll(keyPoint);
            return RespBean.ok("修改成功",key);
        } catch (Exception e) {
            return RespBean.error("修改出错",e.getMessage());
        }
    }

    @DeleteMapping("/KeyPoints")
    public RespBean deleteKeyPoint(@RequestParam("id") String id) {
        try {
            if (resultKeyPoints.delete(id) == 1) {
                return RespBean.ok("删除成功");
            }
            return RespBean.error("未删除数据");
        } catch (Exception e) {
            return RespBean.error("删除出错",e.getMessage());
        }
    }

}
