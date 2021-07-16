package cn.edu.cup.hilly.dataSource.service.mongo.result;

import cn.edu.cup.hilly.dataSource.mapper.mongo.ResultDPLDao;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultAllLineFP;
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
public class ResultDPLService {

    @Autowired
    ResultDPLDao resultDPLDao;
    @Autowired
    MongoTemplate mongoTemplate;

    public void add(ResultDPL resultDPL) {
        resultDPLDao.save(resultDPL);
    }
    public void update(ResultDPL resultDPL) {
//        String id = resultDPL.getProjectId();
        String id = resultDPL.get_id();
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = Update.update("dPL",resultDPL.getDPL());
        mongoTemplate.upsert(query,update, ResultDPL.class,"resultDPL");
    }
    public void updateMap(ResultDPL resultDPL) {
//        String id = resultDPL.getProjectId();
//        String id = resultDPL.get_id();
//        Query query = Query.query(Criteria.where("_id").is(id));
//        Update update = Update.update("dPLMap",resultDPL.getDPLMap());
//        mongoTemplate.upsert(query,update, ResultDPL.class,"resultDPL");
        String projectId = resultDPL.getProjectId();
        Map<Double, double[]> dPLMap = resultDPL.getDPLMap();
        if (dPLMap.size() <= 600) {
            Query query = Query.query(Criteria.where("projectId").is(projectId).and("timeFrame").is(100.00));
            Update update = Update.update("dPLMap",dPLMap);
            mongoTemplate.upsert(query,update, ResultDPL.class,"resultDPL");
        } else if (dPLMap.size() <= 1200) {
            Map<Double, double[]> dPLMap1 = new HashMap<>();
            Map<Double, double[]> dPLMap2 = new HashMap<>();
            for (Map.Entry<Double, double[]> entry : dPLMap.entrySet()) {
                Double key = entry.getKey();
                double[] value = entry.getValue();
                if (key <= 6000.00) {
                    dPLMap1.put(key,value);
                } else {
                    dPLMap2.put(key,value);
                }
            }
            Query query1 = Query.query(Criteria.where("projectId").is(projectId).and("timeFrame").is(100.00));
            Update update1 = Update.update("dPLMap",dPLMap1);
            mongoTemplate.upsert(query1,update1, ResultDPL.class,"resultDPL");

            Query query2 = Query.query(Criteria.where("projectId").is(projectId).and("timeFrame").is(200.00));
            Update update2 = Update.update("dPLMap",dPLMap2);
            mongoTemplate.upsert(query2,update2, ResultDPL.class,"resultDPL");

        } else if (dPLMap.size() <= 1800) {
            Map<Double, double[]> dPLMap1 = new HashMap<>();
            Map<Double, double[]> dPLMap2 = new HashMap<>();
            Map<Double, double[]> dPLMap3 = new HashMap<>();
            for (Map.Entry<Double, double[]> entry : dPLMap.entrySet()) {
                Double key = entry.getKey();
                double[] value = entry.getValue();
                if (key <= 6000.00) {
                    dPLMap1.put(key,value);
                } else if(key <= 12000.00) {
                    dPLMap2.put(key,value);
                } else {
                    dPLMap3.put(key,value);
                }
            }
            Query query1 = Query.query(Criteria.where("projectId").is(projectId).and("timeFrame").is(100.00));
            Update update1 = Update.update("dPLMap",dPLMap1);
            mongoTemplate.upsert(query1,update1, ResultDPL.class,"resultDPL");

            Query query2 = Query.query(Criteria.where("projectId").is(projectId).and("timeFrame").is(200.00));
            Update update2 = Update.update("dPLMap",dPLMap2);
            mongoTemplate.upsert(query2,update2, ResultDPL.class,"resultDPL");

            Query query3 = Query.query(Criteria.where("projectId").is(projectId).and("timeFrame").is(300.00));
            Update update3 = Update.update("dPLMap",dPLMap3);
            mongoTemplate.upsert(query3,update3, ResultDPL.class,"resultDPL");
        }
    }
    public ResultDPL find(String id) {
        Query query = Query.query(Criteria.where("projectId").is(id));
        List<ResultDPL> resultDPLs = mongoTemplate.find(query, ResultDPL.class, "resultDPL");

        ResultDPL resultDPL = new ResultDPL();
        Map<Double, double[]> resultDPLMaps = new HashMap<>();
        for (int i = 0; i < resultDPLs.size(); i++) {
            resultDPLMaps.putAll(resultDPLs.get(i).getDPLMap());
        }

        resultDPL.setDPLMap(resultDPLMaps);
        return resultDPL;
    }

    public void clear(String id) {
        Query query = new Query(Criteria.where("projectId").is(id));
        mongoTemplate.remove(query,ResultDPL.class,"resultDPL");
    }

    public ResultDPL findLast(String id) {
        Query query = Query.query(Criteria.where("projectId").is(id));
        List<ResultDPL> resultDPLs = mongoTemplate.find(query, ResultDPL.class, "resultDPL");

        ResultDPL resultDPL = new ResultDPL();
        Map<Double, double[]> dplMap = resultDPLs.get(resultDPLs.size() - 1).getDPLMap();
        List<Double> mapKey = new ArrayList<>(dplMap.keySet());
        Double lastKey = mapKey.get(mapKey.size() - 1);
        double[] lastDPL = dplMap.get(lastKey);
        Map<Double, double[]> lastDPLMap = new HashMap<>();
        lastDPLMap.put(lastKey,lastDPL);

        resultDPL.setDPLMap(lastDPLMap);
        return resultDPL;
    }
}
