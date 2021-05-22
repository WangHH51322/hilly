package cn.edu.cup.hilly.dataSource.controller.mongodb;

import cn.edu.cup.hilly.dataSource.model.mongo.result.*;
import cn.edu.cup.hilly.dataSource.service.mongo.result.*;
import cn.edu.cup.hilly.dataSource.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/DPL")
    public RespBean getDPL(@RequestParam("id") String id) {
        try {
            ResultDPL resultDPL = resultDPLService.find(id);
            return RespBean.ok("查询成功",resultDPL);
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
        try {
            ResultSimple resultSimple = resultSimpleService.find(id);
            return RespBean.ok("查询成功",resultSimple);
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
}
