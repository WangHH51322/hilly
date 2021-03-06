package cn.edu.cup.hilly.dataSource.service.mongo.result;

import cn.edu.cup.hilly.dataSource.mapper.mongo.KeyPointDao;
import cn.edu.cup.hilly.dataSource.model.mongo.result.KeyPoint;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultDPL;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultDmgHis;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultSimple;
import com.mongodb.bulk.BulkWriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.*;

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
    @Autowired
    ResultSimpleService resultSimpleService;
    @Autowired
    ResultDmgHisService resultDmgHisService;

    public List<KeyPoint> getAll(String id) {
        Query query = Query.query(Criteria.where("projectId").is(id));
        List<KeyPoint> keyPoints = mongoTemplate.find(query, KeyPoint.class, "keyPoint");
        keyPoints.sort(new Comparator<KeyPoint>() {
            @Override
            public int compare(KeyPoint o1, KeyPoint o2) {
                Double d1 = new Double(o1.getMileage());
                Double d2 = new Double(o2.getMileage());
                return d1.compareTo(d2);
            }
        });
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

    public List<KeyPoint> updateAll(String id, List<KeyPoint> keyPoints) {
        for (KeyPoint keyPoint : keyPoints) {
            if (keyPoint.get_id() == null) {
                keyPoint.setProjectId(id);
            }
        }
        Query query = Query.query(Criteria.where("projectId").is(id));
        mongoTemplate.findAllAndRemove(query,KeyPoint.class, "keyPoint");

        BulkOperations operations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, "keyPoint");
        operations.insert(keyPoints);
        operations.execute();

        List<KeyPoint> points = getAll(id);
        points.sort(new Comparator<KeyPoint>() {
            @Override
            public int compare(KeyPoint o1, KeyPoint o2) {
                Double d1 = new Double(o1.getMileage());
                Double d2 = new Double(o2.getMileage());
                return d1.compareTo(d2);
            }
        });
        return points;
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

        ResultSimple resultSimple = resultSimpleService.find(id);
        double[][] lineL = resultSimple.getLineL();
        System.out.println("lineL = ");
        for (int i = 0; i < lineL.length; i++) {
            for (int j = 0; j < lineL[i].length; j++) {
                System.out.print(lineL[i][j] + " ");
            }
            System.out.println();
        }

        ResultDmgHis resultDmgHis = resultDmgHisService.find(id);
        Map<Double, double[]> dMgHisMap = resultDmgHis.getDMgHisMap();

        List<Double> mapKeys = new ArrayList<>(dplMap.keySet());    // ????????????
        Collections.sort(mapKeys);
        for (Double mapKey : mapKeys) {
            System.out.print(mapKey + " ");
        }

        for (int i = 0; i < KeyPoints.size(); i++) {
            double[][] value1 = new double[mapKeys.size()][2];
            double[][] value2 = new double[mapKeys.size()][2];

            KeyPoint keyPoint = KeyPoints.get(i);   // ???????????????
            int keyPointNumb = keyPoint.KeyPointNumb(); // ????????????????????????
            System.out.println("keyPointNumb = " + keyPointNumb);
            int pointI = keyPoint.getI(lineL);  // ???????????????U?????????
            System.out.println("pointI = " + pointI);

            for (int j = 0; j < mapKeys.size(); j++) {
                Double mapKey = mapKeys.get(j); // ????????????
                value1[j][0] = mapKey;
                value2[j][0] = mapKey;
                value1[j][1] = dplMap.get(mapKey)[keyPointNumb];
                value2[j][1] = dMgHisMap.get(mapKey)[pointI - 1];
            }

            keyPoint.setValue1(value1);
            keyPoint.setValue2(value2);
            KeyPoints.set(i,keyPoint);
        }
        return KeyPoints;
    }

}
