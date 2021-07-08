package cn.edu.cup.hilly.dataSource.service.mongo.result;

import cn.edu.cup.hilly.dataSource.mapper.mongo.ResultALFPDao;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultAllLineFP;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultDPL;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultLgHis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

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
    public void add(ResultAllLineFP result,double timeFrame) {
        result.setTimeFrame(100.00);
        resultALSPDao.save(result);
        result.setTimeFrame(200.00);
        resultALSPDao.save(result);
        result.setTimeFrame(300.00);
        resultALSPDao.save(result);
    }

    public void updateMap(ResultAllLineFP resultAllLineFP) {
        String projectId = resultAllLineFP.getProjectId();
        Map<Double, double[]> allLineFPMap = resultAllLineFP.getAllLineFPMap();
        if (allLineFPMap.size() <= 600) {
            Query query = Query.query(Criteria.where("projectId").is(projectId).and("timeFrame").is(100.00));
            Update update = Update.update("allLineFPMap",allLineFPMap);
            mongoTemplate.upsert(query,update, ResultAllLineFP.class,"resultAllLineFP");
        } else if (allLineFPMap.size() <= 1200) {
            Map<Double, double[]> allLineFPMap1 = new HashMap<>();
            Map<Double, double[]> allLineFPMap2 = new HashMap<>();
            for (Map.Entry<Double, double[]> entry : allLineFPMap.entrySet()) {
                Double key = entry.getKey();
                double[] value = entry.getValue();
                if (key > 6000.00) {
                    allLineFPMap2.put(key,value);
                }
            }
            Query query = Query.query(Criteria.where("projectId").is(projectId).and("timeFrame").is(200.00));
            Update update = Update.update("allLineFPMap",allLineFPMap2);
            mongoTemplate.upsert(query,update, ResultAllLineFP.class,"resultAllLineFP");
        } else if (allLineFPMap.size() <= 1800) {
            Map<Double, double[]> newAllLineFPMap = new HashMap<>();
            for (Map.Entry<Double, double[]> entry : allLineFPMap.entrySet()) {
                Double key = entry.getKey();
                double[] value = entry.getValue();
                if (key > 12000.00) {
                    newAllLineFPMap.put(key,value);
                }
            }
            Query query = Query.query(Criteria.where("projectId").is(projectId).and("timeFrame").is(300.00));
            Update update = Update.update("allLineFPMap",newAllLineFPMap);
            mongoTemplate.upsert(query,update, ResultAllLineFP.class,"resultAllLineFP");
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
//        ResultAllLineFP resultAllLineFP = resultALSPDao.findById(id).get();
        Query query = new Query(Criteria.where("_id").is(id));
        ResultAllLineFP resultAllLineFP = mongoTemplate.findOne(query, ResultAllLineFP.class,"resultAllLineFP");
        Map<Double, List<ResultAllLineFP.outPut>> doubleListMap = resultAllLineFP.convertALFP(248.0);
        return doubleListMap;
    }
}
