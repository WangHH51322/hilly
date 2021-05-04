package cn.edu.cup.hilly.dataSource.controller.mongodb;

import cn.edu.cup.base.CommonProvider;
import cn.edu.cup.hilly.calculate.hilly.large.Project;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultAllLineFP;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultDPL;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultLgHis;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultSimple;
import cn.edu.cup.hilly.dataSource.model.rabbitmq.PushMsgProducer;
import cn.edu.cup.hilly.dataSource.model.rabbitmq.WiselyMessage;
import cn.edu.cup.hilly.dataSource.service.mongo.HillyService;
import cn.edu.cup.hilly.dataSource.model.mongo.DataMap;
import cn.edu.cup.hilly.dataSource.model.mongo.Hilly;
import cn.edu.cup.hilly.dataSource.service.mongo.result.ResultALFPService;
import cn.edu.cup.hilly.dataSource.service.mongo.result.ResultDPLService;
import cn.edu.cup.hilly.dataSource.service.mongo.result.ResultLgHisService;
import cn.edu.cup.hilly.dataSource.service.mongo.result.ResultSimpleService;
import cn.edu.cup.hilly.dataSource.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/thread")
public class ThreadController {

    @Autowired
    HillyService hillyService;
    @Autowired
    ResultDPLService resultDPLService;
    @Autowired
    ResultALFPService resultALFPService;
    @Autowired
    ResultLgHisService resultLgHisService;
    @Autowired
    ResultSimpleService resultSimpleService;
    @Autowired
    PushMsgProducer sender;

    /**
     * 调用方法求解
     * @param id
     * @return
     */
    @GetMapping("/run")
    public RespBean run(@RequestParam("id") String id){
        WiselyMessage msg = new WiselyMessage();
        try {
            Project project = new Project();
            Thread thread = new Thread(project);
            Hilly hilly = hillyService.getHillyById(id);
            Map<String, Object> dataMap = DataMap.getDataMap(hilly);

            CommonProvider commonProvider = new CommonProvider();
            commonProvider.setDataMap(dataMap);
            commonProvider.startDataRequirementProcess(project);
            Project.recoverThread();
            thread.start();
            ResultDPL resultDPL = new ResultDPL();
            //                        resultDPL.setProjectId(id);
            resultDPL.set_id(id);
            while (thread.isAlive()) {
                try {
                    Thread.sleep(1 * 1000); //设置暂停的时间 1 秒
                    if (!project.isLocked()) {
                        Map<Integer, double[]> dpl = project.getDPL();
                        resultDPL.setDPLMap(dpl);
                        resultDPLService.updateMap(resultDPL);
                        msg.setName("hello");
                        msg.setRoutingKey("rk_pushmsg");
                        msg.setMsg("这是一条来自后端的消息");
                        msg.setObject(resultDPL);
                        System.out.println("save data");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                sender.send(msg);
            }
            return RespBean.ok("开始计算");
        } catch (Exception e) {
            return RespBean.error("启动失败",e.getClass().getName());
        }

    }

    @GetMapping("/run2")
    public RespBean run2(@RequestParam("id") String id){
        try {
            Project project = new Project();
            Thread thread = new Thread(project);
            Hilly hilly = hillyService.getHillyById(id);
            Map<String, Object> dataMap = DataMap.getDataMap(hilly);

            CommonProvider commonProvider = new CommonProvider();
            commonProvider.setDataMap(dataMap);
            commonProvider.startDataRequirementProcess(project);
            Project.recoverThread();
            thread.start();
            /**
             * 数据存储,输出
             */
            ResultDPL resultDPL = new ResultDPL();
            resultDPL.set_id(id);
            ResultAllLineFP resultAllLineFP = new ResultAllLineFP();
            resultAllLineFP.set_id(id);
            ResultLgHis resultLgHis = new ResultLgHis();
            resultLgHis.set_id(id);
            ResultSimple resultSimple = new ResultSimple();
            resultSimple.set_id(id);
            while (thread.isAlive()) {
                try {
                    Thread.sleep(1 * 1000); //设置暂停的时间 1 秒
                    if (!project.isLocked()) {
                        Map<Integer, double[]> dpl = project.getDPL();
                        resultDPL.setDPLMap(dpl);
                        resultDPLService.updateMap(resultDPL);

                        Map<Integer, double[]> aLineFP = project.getALineFP();
                        resultAllLineFP.setALineFPMap(aLineFP);
                        resultALFPService.updateMap(resultAllLineFP);

                        Map<Integer, double[]> lgHis = project.getLgHis();
                        resultLgHis.setLgHisMap(lgHis);
                        resultLgHisService.updateMap(resultLgHis);

                        double[][] pigV = project.getPigV();
                        resultSimple.setPigV(pigV);
                        resultSimpleService.updatePigV(resultSimple);
                        double[][] pigL = project.getPigL();
                        resultSimple.setPigL(pigL);
                        resultSimpleService.updatePigL(resultSimple);
                        double[][] aLSP = project.getaLSP();
                        resultSimple.setALSP(aLSP);
                        resultSimpleService.updateALSP(resultSimple);

                        System.out.println("save data");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return RespBean.ok("开始计算");
        } catch (Exception e) {
            return RespBean.error("启动失败",e.getClass().getName());
        }
    }

    @GetMapping("/pause")
    public RespBean pause(){
        try {
            Project.pauseThread();
            return RespBean.ok("程序中断");
        } catch (Exception e) {
            return RespBean.error("程序中断失败!!!",e.getClass().getName());
        }
    }
    @GetMapping("/resume")
    public RespBean resume(){
        try {
            Project.resumeThread();
            return RespBean.ok("程序恢复");
        } catch (Exception e) {
            return RespBean.error("程序恢复失败!!!",e.getClass().getName());
        }
    }
    @GetMapping("/release")
    public RespBean release(){
        try {
            Project.releaseThread();
            return RespBean.ok("程序结束");
        } catch (Exception e) {
            return RespBean.error("程序结束失败!!!",e.getClass().getName());
        }
    }
}
