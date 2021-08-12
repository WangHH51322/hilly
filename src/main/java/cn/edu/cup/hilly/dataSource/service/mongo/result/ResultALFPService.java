package cn.edu.cup.hilly.dataSource.service.mongo.result;

import cn.edu.cup.hilly.dataSource.mapper.mongo.ResultALFPDao;
import cn.edu.cup.hilly.dataSource.model.mongo.Hilly;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultAllLineFP;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultDPL;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultLgHis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author CodeChap
 * @date 2021-05-04- 19:22
 */
@Service
public class ResultALFPService {

    @Autowired
    ResultALFPDao resultALSPDao;
    @Autowired
    MongoTemplate mongoTemplate;
    public void add(ResultAllLineFP result) {
        resultALSPDao.save(result);
    }

    public void updateMap(ResultAllLineFP resultAllLineFP) {
        String projectId = resultAllLineFP.getProjectId();
        Map<Double, double[]> allLineFPMap = resultAllLineFP.getAllLineFPMap();
        if (allLineFPMap.size() <= 600) {
            Query query1 = Query.query(Criteria.where("projectId").is(projectId).and("timeFrame").is(100.00));
            Update update = Update.update("allLineFPMap",allLineFPMap);
            mongoTemplate.upsert(query1,update, ResultAllLineFP.class,"resultAllLineFP");

//            Query query2 = Query.query(Criteria.where("projectId").is(projectId).and("timeFrame").is(200.00));
//            mongoTemplate.remove(query2, ResultAllLineFP.class,"resultAllLineFP");
//            Query query3 = Query.query(Criteria.where("projectId").is(projectId).and("timeFrame").is(300.00));
//            mongoTemplate.remove(query3, ResultAllLineFP.class,"resultAllLineFP");
        } else if (allLineFPMap.size() <= 1200) {
            Map<Double, double[]> allLineFPMap1 = new HashMap<>();
            Map<Double, double[]> allLineFPMap2 = new HashMap<>();
            for (Map.Entry<Double, double[]> entry : allLineFPMap.entrySet()) {
                Double key = entry.getKey();
                double[] value = entry.getValue();
                if (key <= 6000.00) {
                    allLineFPMap1.put(key,value);
                } else {
                    allLineFPMap2.put(key,value);
                }
            }
            Query query1 = Query.query(Criteria.where("projectId").is(projectId).and("timeFrame").is(100.00));
            Update update1 = Update.update("allLineFPMap",allLineFPMap1);
            mongoTemplate.upsert(query1,update1, ResultAllLineFP.class,"resultAllLineFP");

            Query query2 = Query.query(Criteria.where("projectId").is(projectId).and("timeFrame").is(200.00));
            Update update2 = Update.update("allLineFPMap",allLineFPMap2);
            mongoTemplate.upsert(query2,update2, ResultAllLineFP.class,"resultAllLineFP");

//            Query query3 = Query.query(Criteria.where("projectId").is(projectId).and("timeFrame").is(300.00));
//            mongoTemplate.remove(query3, ResultAllLineFP.class,"resultAllLineFP");

        } else if (allLineFPMap.size() <= 1800) {
            Map<Double, double[]> allLineFPMap1 = new HashMap<>();
            Map<Double, double[]> allLineFPMap2 = new HashMap<>();
            Map<Double, double[]> allLineFPMap3 = new HashMap<>();
            for (Map.Entry<Double, double[]> entry : allLineFPMap.entrySet()) {
                Double key = entry.getKey();
                double[] value = entry.getValue();
                if (key <= 6000.00) {
                    allLineFPMap1.put(key,value);
                } else if(key <= 12000.00) {
                    allLineFPMap2.put(key,value);
                } else {
                    allLineFPMap3.put(key,value);
                }
            }
            Query query1 = Query.query(Criteria.where("projectId").is(projectId).and("timeFrame").is(100.00));
            Update update1 = Update.update("allLineFPMap",allLineFPMap1);
            mongoTemplate.upsert(query1,update1, ResultAllLineFP.class,"resultAllLineFP");

            Query query2 = Query.query(Criteria.where("projectId").is(projectId).and("timeFrame").is(200.00));
            Update update2 = Update.update("allLineFPMap",allLineFPMap2);
            mongoTemplate.upsert(query2,update2, ResultAllLineFP.class,"resultAllLineFP");

            Query query3 = Query.query(Criteria.where("projectId").is(projectId).and("timeFrame").is(300.00));
            Update update3 = Update.update("allLineFPMap",allLineFPMap3);
            mongoTemplate.upsert(query3,update3, ResultAllLineFP.class,"resultAllLineFP");
        }
    }

    public ResultAllLineFP find(String id) {
//        ResultAllLineFP resultAllLineFP = resultALSPDao.findById(id).get();
        Query query = new Query(Criteria.where("_id").is(id));
        ResultAllLineFP resultAllLineFP = mongoTemplate.findOne(query, ResultAllLineFP.class,"resultAllLineFP");
        System.out.println(resultAllLineFP.getAllLineFPMap());
        return resultAllLineFP;
    }

    public Map<Double, List<ResultAllLineFP.outPut>> find2(String id) {
        Query query2 = new Query(Criteria.where("projectId").is(id));
        List<ResultAllLineFP> resultAllLineFPs = mongoTemplate.find(query2, ResultAllLineFP.class, "resultAllLineFP");
        ResultAllLineFP resultAllLineFP = new ResultAllLineFP();
        Map<Double, double[]> allLineFPMaps = new HashMap<>();

        for (int i = 0; i < resultAllLineFPs.size(); i++) {
            allLineFPMaps.putAll(resultAllLineFPs.get(i).getAllLineFPMap());
        }

        resultAllLineFP.setAllLineFPMap(allLineFPMaps);
        return resultAllLineFP.convertALFP(0.0);
    }

    public Map<Double, List<ResultAllLineFP.outPut>> findLast(String id) {
        Query query2 = new Query(Criteria.where("projectId").is(id));
        List<ResultAllLineFP> resultAllLineFPs = mongoTemplate.find(query2, ResultAllLineFP.class, "resultAllLineFP");
        ResultAllLineFP resultAllLineFP = new ResultAllLineFP();
        Map<Double, double[]> allLineFPMaps = new HashMap<>();
        Map<Double, double[]> allLineFPMap = new HashMap<>();

        for (int i = 0; i < resultAllLineFPs.size(); i++) {
            allLineFPMaps.putAll(resultAllLineFPs.get(i).getAllLineFPMap());
        }

        List<Double> mapKeys = new ArrayList<>(allLineFPMaps.keySet());
        double totalTime = mapKeys.get(mapKeys.size() - 1);
        double startTime = 0.0;
        while (startTime < totalTime) {
            allLineFPMap.put(startTime,allLineFPMaps.get(startTime));
            startTime += 60.0;
        }
        if ( totalTime % 60.0 != 0.0) {   // 将最后一时刻的值存入map
            allLineFPMap.put(totalTime,allLineFPMaps.get(totalTime));
        }
        resultAllLineFP.setAllLineFPMap(allLineFPMap);

        Map<Double, List<ResultAllLineFP.outPut>> convertALFP = resultAllLineFP.convertALFP(0.0);



        return convertALFP;
    }

    public void clear(String id) {
        Query query = new Query(Criteria.where("projectId").is(id));
        mongoTemplate.remove(query,ResultAllLineFP.class, "resultAllLineFP");
    }
}
