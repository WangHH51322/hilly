package cn.edu.cup.hilly.dataSource.service.mongo.result;

import cn.edu.cup.hilly.dataSource.mapper.mongo.ResultDHLDao;
import cn.edu.cup.hilly.dataSource.mapper.mongo.ResultDHLDao;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultDHL;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultDHL;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultDPL;
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

@Service
public class ResultDHLService {

    @Autowired
    ResultDHLDao resultDHLDao;
    @Autowired
    MongoTemplate mongoTemplate;

    public void add(ResultDHL resultDHL) {
        resultDHLDao.save(resultDHL);
    }
    public void update(ResultDHL resultDHL) {
        String id = resultDHL.get_id();
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = Update.update("dHL",resultDHL.getDHL());
        mongoTemplate.upsert(query,update, ResultDHL.class,"resultDHL");
    }
    public void updateMap(ResultDHL resultDHL) {
//        String id = resultDHL.get_id();
//        Query query = Query.query(Criteria.where("_id").is(id));
//        Update update = Update.update("dHLMap",resultDHL.getDHLMap());
//        System.out.println();
//        mongoTemplate.upsert(query,update, ResultDHL.class,"resultDHL");
        String projectId = resultDHL.getProjectId();
        Map<Double, double[]> dHLMap = resultDHL.getDHLMap();
        if (dHLMap.size() <= 600) {
            Query query = Query.query(Criteria.where("projectId").is(projectId).and("timeFrame").is(100.00));
            Update update = Update.update("dHLMap",dHLMap);
            mongoTemplate.upsert(query,update, ResultDHL.class,"resultDHL");
        } else if (dHLMap.size() <= 1200) {
            Map<Double, double[]> dHLMap1 = new HashMap<>();
            Map<Double, double[]> dHLMap2 = new HashMap<>();
            for (Map.Entry<Double, double[]> entry : dHLMap.entrySet()) {
                Double key = entry.getKey();
                double[] value = entry.getValue();
                if (key <= 6000.00) {
                    dHLMap1.put(key,value);
                } else {
                    dHLMap2.put(key,value);
                }
            }
            Query query1 = Query.query(Criteria.where("projectId").is(projectId).and("timeFrame").is(100.00));
            Update update1 = Update.update("dHLMap",dHLMap1);
            mongoTemplate.upsert(query1,update1, ResultDHL.class,"resultDHL");

            Query query2 = Query.query(Criteria.where("projectId").is(projectId).and("timeFrame").is(200.00));
            Update update2 = Update.update("dHLMap",dHLMap2);
            mongoTemplate.upsert(query2,update2, ResultDHL.class,"resultDHL");

        } else if (dHLMap.size() <= 1800) {
            Map<Double, double[]> dHLMap1 = new HashMap<>();
            Map<Double, double[]> dHLMap2 = new HashMap<>();
            Map<Double, double[]> dHLMap3 = new HashMap<>();
            for (Map.Entry<Double, double[]> entry : dHLMap.entrySet()) {
                Double key = entry.getKey();
                double[] value = entry.getValue();
                if (key <= 6000.00) {
                    dHLMap1.put(key,value);
                } else if(key <= 12000.00) {
                    dHLMap2.put(key,value);
                } else {
                    dHLMap3.put(key,value);
                }
            }
            Query query1 = Query.query(Criteria.where("projectId").is(projectId).and("timeFrame").is(100.00));
            Update update1 = Update.update("dHLMap",dHLMap1);
            mongoTemplate.upsert(query1,update1, ResultDHL.class,"resultDHL");

            Query query2 = Query.query(Criteria.where("projectId").is(projectId).and("timeFrame").is(200.00));
            Update update2 = Update.update("dHLMap",dHLMap2);
            mongoTemplate.upsert(query2,update2, ResultDHL.class,"resultDHL");

            Query query3 = Query.query(Criteria.where("projectId").is(projectId).and("timeFrame").is(300.00));
            Update update3 = Update.update("dHLMap",dHLMap3);
            mongoTemplate.upsert(query3,update3, ResultDHL.class,"resultDHL");
        }
    }
    public ResultDHL find(String id) {
        Query query = Query.query(Criteria.where("projectId").is(id));
        List<ResultDHL> resultDHLs = mongoTemplate.find(query, ResultDHL.class, "resultDHL");

        ResultDHL resultDHL = new ResultDHL();
        Map<Double, double[]> resultDHLMaps = new HashMap<>();
        for (int i = 0; i < resultDHLs.size(); i++) {
            resultDHLMaps.putAll(resultDHLs.get(i).getDHLMap());
        }

        resultDHL.setDHLMap(resultDHLMaps);
        return resultDHL;
    }


    public ResultDHL findLast(String id) {
        Query query = Query.query(Criteria.where("projectId").is(id));
        List<ResultDHL> resultDHLs = mongoTemplate.find(query, ResultDHL.class, "resultDHL");

        ResultDHL resultDHL = new ResultDHL();
        Map<Double, double[]> resultDHLMaps = new HashMap<>();
        Map<Double, double[]> resultDHLMap = new HashMap<>();

        for (int i = 0; i < resultDHLs.size(); i++) {
            resultDHLMaps.putAll(resultDHLs.get(i).getDHLMap());
        }

        List<Double> mapKeys = new ArrayList<>(resultDHLMaps.keySet());
        double totalTime = mapKeys.get(mapKeys.size() - 1);
        double startTime = 0.0;
        while (startTime < totalTime) {
            resultDHLMap.put(startTime,resultDHLMaps.get(startTime));
            startTime += 60.0;
        }
        if ( totalTime % 60.0 != 0.0) {   // 将最后一时刻的值存入map
            resultDHLMap.put(totalTime,resultDHLMaps.get(totalTime));
        }
        resultDHL.setDHLMap(resultDHLMap);
        return resultDHL;
    }

    public void clear(String id) {
        Query query = new Query(Criteria.where("projectId").is(id));
        mongoTemplate.remove(query, ResultDHL.class, "resultDHL");
    }
}
