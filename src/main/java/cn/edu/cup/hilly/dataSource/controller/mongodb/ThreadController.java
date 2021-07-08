package cn.edu.cup.hilly.dataSource.controller.mongodb;

import cn.edu.cup.base.CommonProvider;
import cn.edu.cup.hilly.calculate.hilly.large.Project;
import cn.edu.cup.hilly.dataSource.model.ExcelFile;
import cn.edu.cup.hilly.dataSource.model.mongo.result.*;
//import cn.edu.cup.hilly.dataSource.model.rabbitmq.PushMsgProducer;
//import cn.edu.cup.hilly.dataSource.model.rabbitmq.WiselyMessage;
import cn.edu.cup.hilly.dataSource.model.mongo.stationList.StationPumps;
import cn.edu.cup.hilly.dataSource.model.rabbitmq.PushMsgProducer;
import cn.edu.cup.hilly.dataSource.model.rabbitmq.WiselyMessage;
import cn.edu.cup.hilly.dataSource.service.FileService;
import cn.edu.cup.hilly.dataSource.service.mongo.HillyService;
import cn.edu.cup.hilly.dataSource.model.mongo.DataMap;
import cn.edu.cup.hilly.dataSource.model.mongo.Hilly;
import cn.edu.cup.hilly.dataSource.service.mongo.HillyStationPumpsService;
import cn.edu.cup.hilly.dataSource.service.mongo.HillyStationService;
import cn.edu.cup.hilly.dataSource.service.mongo.result.*;
import cn.edu.cup.hilly.dataSource.utils.RespBean;
import cn.edu.cup.hilly.dataSource.utils.SizeChange;
import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/thread")
public class ThreadController {

    @Autowired
    HillyService hillyService;
    @Autowired
    ResultULService resultULService;
    @Autowired
    ResultDPLService resultDPLService;
    @Autowired
    ResultDHLService resultDHLService;
    @Autowired
    ResultALFPService resultALFPService;
    @Autowired
    ResultLgHisService resultLgHisService;
    @Autowired
    ResultPgHisService resultPgHisService;
    @Autowired
    ResultMHisService resultMHisService;
    @Autowired
    ResultHSSService resultHSSService;
    @Autowired
    ResultSimpleService resultSimpleService;
    @Autowired
    HillyStationService hillyStationService;
    @Autowired
    DataMap dataMap;
    @Autowired
    PushMsgProducer sender;
    @Autowired
    FileService fileService;


    @GetMapping("/test")
    public RespBean runTest(@RequestParam("id") String id) {
//        DataMap dataMap = new DataMap();
        Map<String, Object> stringObjectMap = dataMap.convertDataMap(id);
        return RespBean.ok("转换成功",stringObjectMap);
    }

    @GetMapping("/test/rabbit")
    public void rabbitTest() throws IOException {
        WiselyMessage msg = new WiselyMessage();
        WiselyMessage msg2 = new WiselyMessage();
        WiselyMessage msg3 = new WiselyMessage();
        int i = 5;
        while (i > 0) {
            i --;
            try {
                Thread.sleep(1 * 1000); //设置暂停的时间 1 秒
                    msg.setName("hello");
                    msg.setRoutingKey("rk_pushmsg3");
                    msg.setMsg("这是一条来自后端的消息");
                    System.out.println("save data");
                } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1 * 1000); //设置暂停的时间 1 秒
                    msg2.setName("hello2");
                    msg2.setRoutingKey("rk_pushmsg2");
                    msg2.setMsg("这是一条来自后端的消息2");
                    System.out.println("save data2");
                } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1 * 1000); //设置暂停的时间 1 秒
                    msg3.setName("hello3");
                    msg3.setRoutingKey("rk_pushmsg");
                    msg3.setMsg("这是一条来自后端的消息");
                    System.out.println("save data3");
                } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sender.send(msg);
            sender.send(msg2);
            sender.send(msg3);
        }
    }

    /**
     * 调用方法求解
     * @param id
     * @return
     */
    @GetMapping("/run")
    public RespBean run(@RequestParam("id") String id){
//        WiselyMessage msg = new WiselyMessage();
        try {
            Project project = new Project();
            Thread thread = new Thread(project);
//            Hilly hilly = hillyService.getHillyById(id);
//            StationPumps stationPump = hillyStationService.getStationPump(id);
//            Map<String, Object> dataMap = DataMap.getDataMap(hilly);
            Map<String, Object> data = dataMap.convertDataMap(id);
            CommonProvider commonProvider = new CommonProvider();
            commonProvider.setDataMap(data);
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
                        Map<Double, double[]> dpl = project.getDPL();
                        resultDPL.setDPLMap(dpl);
                        resultDPLService.updateMap(resultDPL);
//                        msg.setName("hello");
//                        msg.setRoutingKey("rk_pushmsg");
//                        msg.setMsg("这是一条来自后端的消息");
//                        msg.setObject(resultDPL);
                        System.out.println("save data");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                sender.send(msg);
            }
            return RespBean.ok("开始计算");
        } catch (Exception e) {
            return RespBean.error("启动失败",e.getClass().getName());
        }

    }

    @GetMapping("/run2")
    public RespBean run2(@RequestParam("id") String id){
        WiselyMessage msg = new WiselyMessage();
        WiselyMessage msg2 = new WiselyMessage();
        WiselyMessage msg3 = new WiselyMessage();
        ExcelFile excelFile = fileService.find(id);
//        double[][] lz = excelFile.getLz();  //三角式地形数据
        Integer inum = excelFile.getInum();
        try {
            Project project = new Project();
//            project.setLz(lz);
            project.setInum(inum);
            Thread thread = new Thread(project);
            Map<String, Object> data = dataMap.convertDataMap(id);
            CommonProvider commonProvider = new CommonProvider();
            commonProvider.setDataMap(data);
            commonProvider.startDataRequirementProcess(project);
            Project.recoverThread();
            thread.start();
            /**
             * 数据存储,输出
             */

            ResultULocation resultULocation = new ResultULocation();
            resultULocation.setHillyId(id);

            ResultDPL resultDPL = new ResultDPL();
            resultDPL.set_id(id);
//            resultDPL.setLz(lz_out);
//            resultDPLService.add(resultDPL);
//
            ResultDHL resultDHL = new ResultDHL();
            resultDHL.set_id(id);
//            resultDHL.setLz(lz_out);
//            resultDHLService.add(resultDHL);
//
            ResultAllLineFP resultAllLineFP = new ResultAllLineFP();
            resultAllLineFP.setProjectId(id);
//            resultAllLineFP.setLz(lz_out);
//            resultALFPService.add(resultAllLineFP);

            ResultLgHis resultLgHis = new ResultLgHis();
            resultLgHis.set_id(id);
            ResultPgHis resultPgHis = new ResultPgHis();
            resultPgHis.set_id(id);
            ResultMHis resultMHis = new ResultMHis();
            resultMHis.set_id(id);
            ResultHSS resultHSS = new ResultHSS();
            resultHSS.set_id(id);
            ResultSimple resultSimple = new ResultSimple();
            resultSimple.set_id(id);
            boolean in = true;
            while (thread.isAlive()) {
                if (!project.isLocked()) {
                    try {
                        Thread.sleep(5 * 1000); //设置暂停的时间 1 秒
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //计算过程中的地形,用于数据展示的x轴
//                        if (in) {
//                            double[][] lz_out = project.getLz_out();
//                            if (lz_out != null) {
//                                resultDPL.setLz(lz_out);
//                                resultDPLService.add(resultDPL);
//                                resultDHL.setLz(lz_out);
//                                resultDHLService.add(resultDHL);
//                                resultAllLineFP.setLz(lz_out);
////                                resultALFPService.add(resultAllLineFP,100.00);   // 每隔100h存一个collection
//                                System.out.println("进入成功!!!");
//                                in = false;
//                            }
//                        }
                    double[][] varPara_allLineFP = project.getVarPara_allLineFP();
                    SizeChange allLineFPChange = new SizeChange(varPara_allLineFP);
                    Map<Double, double[]> allLineFPAfterChange = allLineFPChange.ResultAfterChange(4,2.5);
                    resultAllLineFP.setAllLineFPMap(allLineFPAfterChange);
                    msg.setName("allLineFP");
                    msg.setRoutingKey("curve_allLineFP");
                    msg.setMsg("这是一条来自后端的resultAllLineFP");
                    msg.setObject(resultAllLineFP);
                    resultALFPService.updateMap(resultAllLineFP);

                    double[][] varPara_dPL = project.getVarPara_dPL();
                    SizeChange dPLChange = new SizeChange(varPara_dPL);
                    Map<Double, double[]> dPLAfterChange = dPLChange.ResultAfterChange(4,2.5);
                    resultDPL.setDPLMap(dPLAfterChange);
                    msg2.setName("dPL");
                    msg2.setRoutingKey("curve_dPL");
                    msg2.setMsg("这是一条来自后端的resultDPL");
                    msg2.setObject(resultDPL);
//                        resultDPLService.updateMap(resultDPL);
//                        msg.setObject(resultDPL);
//                        System.out.println("save data");

                    double[][] varPara_dHL = project.getVarPara_dHL();
                    SizeChange dHLChange = new SizeChange(varPara_dHL);
                    Map<Double, double[]> dHLAfterChange = dHLChange.ResultAfterChange(4, 2.5);
                    resultDHL.setDHLMap(dHLAfterChange);
                    msg3.setName("dHL");
                    msg3.setRoutingKey("curve_dHL");
                    msg3.setMsg("这是一条来自后端的resultDHL");
                    msg3.setObject(resultDHL);
//                        resultDHLService.updateMap(resultDHL);

//
//                        /**
//                         * 传输数据
//                         */
////                        msg.setName("hello");
////                        msg.setRoutingKey("rk_pushmsg3");
////                        msg.setMsg("这是一条来自后端的消息");
//                        /**
//                         * U型管段地形数据
//                         */
//                        double[] uLocation = project.getULocation();
////                        if (uLocation != null) {
//                        resultULocation.setULocation(uLocation);
//                        resultULService.updateMap(resultULocation);
////                        msg.setULocation(uLocation);
////                        }
//
//                        Map<Double, double[]> dpl = project.getDPL();
//                        resultDPL.setDPLMap(dpl);
//                        resultDPLService.updateMap(resultDPL);
////                        msg.setObject(resultDPL);
////                        System.out.println("save data");
//
//                        Map<Double, double[]> dhl = project.getDHL();
//                        resultDHL.setDHLMap(dhl);
//                        resultDHLService.updateMap(resultDHL);
//
//                        Map<Double, double[]> aLineFP = project.getALineFP();
//                        resultAllLineFP.setAllLineFPMap(aLineFP);
//                        resultALFPService.updateMap(resultAllLineFP);
//
////                        System.out.println("save data");
                    sender.send(msg);
                    sender.send(msg2);
                    sender.send(msg3);
                }
            }
            /**
             * 计算结束后存储数据
             */
            Map<Double, double[]> lgHis = project.getLgHis();
            resultLgHis.setLgHisMap(lgHis);
            resultLgHisService.updateMap(resultLgHis);

            Map<Double, double[]> pgHis = project.getPgHis();
            resultPgHis.setPgHisMap(pgHis);
            resultPgHisService.updateMap(resultPgHis);

            Map<Double, double[]> mHis = project.getmHis();
            resultMHis.setMHisMap(mHis);
            resultMHisService.updateMap(resultMHis);

            Map<String, Map<Double, double[]>> hSS = project.getHSS();
            resultHSS.setHSSMap(hSS);
            resultHSSService.updateMap(resultHSS);

            double[][] mG = project.getmG();
            resultSimple.setMG(mG);
            resultSimpleService.updateMG(resultSimple);
            double[][] pigV = project.getPigV();
            resultSimple.setPigV(pigV);
            resultSimpleService.updatePigV(resultSimple);
            double[][] pigL = project.getPigL();
            resultSimple.setPigL(pigL);
            resultSimpleService.updatePigL(resultSimple);
            double[][] dMgP = project.getDMgP();
            resultSimple.setDMgP(dMgP);
            resultSimpleService.updateDMgP(resultSimple);
            double[][] aLSP = project.getaLSP();
            resultSimple.setALSP(aLSP);
            resultSimpleService.updateALSP(resultSimple);

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
