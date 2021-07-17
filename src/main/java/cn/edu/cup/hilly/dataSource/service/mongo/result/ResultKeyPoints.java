package cn.edu.cup.hilly.dataSource.service.mongo.result;

import cn.edu.cup.hilly.dataSource.mapper.mongo.KeyPointDao;
import cn.edu.cup.hilly.dataSource.model.mongo.result.KeyPoint;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultDPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author CodeChap
 * @date 2021-07-17 21:04
 * @description ResultKeyPoints
 */
@Service
public class ResultKeyPoints {

    @Autowired
    KeyPointDao keyPointDao;
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    ResultDPLService resultDPLService;

    public List<KeyPoint> getAll(String id) {
        Query query = Query.query(Criteria.where("projectId").is(id));
        List<KeyPoint> keyPoints = mongoTemplate.find(query, KeyPoint.class, "keyPoint");
        return keyPoints;
    }

    public KeyPoint insert(KeyPoint keyPoint) {
        return keyPointDao.save(keyPoint);
    }

    public KeyPoint update(KeyPoint keyPoint) {
        String id = keyPoint.get_id();
        keyPointDao.deleteById(id);
        return keyPointDao.save(keyPoint);
    }

    public int delete(String id) {
        keyPointDao.deleteById(id);
        Query query = Query.query(Criteria.where("_id").is(id));
        if (mongoTemplate.findOne(query, KeyPoint.class, "keyPoint") == null) {
            return 1;
        }
        return 0;
    }

    public List<KeyPoint> getAllData(String id) {
        List<KeyPoint> KeyPoints = getAll(id);
        ResultDPL resultDPL = resultDPLService.findLast(id);
        Map<Double, double[]> dplMap = resultDPL.getDPLMap();

        List<Double> mapKeys = new ArrayList<>(dplMap.keySet());    // 所有时刻

        for (int i = 0; i < KeyPoints.size(); i++) {
            double[][] value1 = new double[mapKeys.size()][2];

            KeyPoint keyPoint = KeyPoints.get(i);   // 当前关键点
            int keyPointNumb = keyPoint.KeyPointNumb(); // 当前关键点所在列
            for (int j = 0; j < mapKeys.size(); j++) {
                Double mapKey = mapKeys.get(j); // 当前时刻
                value1[j][0] = mapKey;
                value1[j][1] = dplMap.get(mapKey)[keyPointNumb];
            }

            keyPoint.setValue1(value1);
            KeyPoints.set(i,keyPoint);
        }
        return KeyPoints;
    }
}
