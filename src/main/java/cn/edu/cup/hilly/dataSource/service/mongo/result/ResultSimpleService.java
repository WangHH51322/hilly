package cn.edu.cup.hilly.dataSource.service.mongo.result;

import cn.edu.cup.hilly.dataSource.mapper.mongo.ResultDPLDao;
import cn.edu.cup.hilly.dataSource.mapper.mongo.ResultSimpleDao;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultDPL;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultMHis;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultSimple;
import cn.edu.cup.hilly.dataSource.model.mongo.stationList.Station;
import cn.edu.cup.hilly.dataSource.service.mongo.HillyStationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ResultSimpleService {

    @Autowired
    ResultSimpleDao resultSimpleDao;
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    HillyStationService stationService;

//    public void update(ResultSimple resultSimple) {
//        String id = resultSimple.get_id();
//        Query query = Query.query(Criteria.where("_id").is(id));
//        mongoTemplate.upsert()
//    }

    public void updateLZ(ResultSimple resultSimple) {
        String id = resultSimple.get_id();
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = Update.update("lz",resultSimple.getLz());
        mongoTemplate.upsert(query,update, ResultSimple.class,"resultSimple");
    }
    public void updateMG(ResultSimple resultSimple) {
        String id = resultSimple.get_id();
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = Update.update("mG",resultSimple.getMG());
        mongoTemplate.upsert(query,update, ResultSimple.class,"resultSimple");
    }
    public void updatePigV(ResultSimple resultSimple) {
        String id = resultSimple.get_id();
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = Update.update("pigV",resultSimple.getPigV());
        mongoTemplate.upsert(query,update, ResultSimple.class,"resultSimple");
    }

    public void updatePigL(ResultSimple resultSimple) {
        String id = resultSimple.get_id();
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = Update.update("pigL",resultSimple.getPigL());
        mongoTemplate.upsert(query,update, ResultSimple.class,"resultSimple");
    }
    public void updateDMgP(ResultSimple resultSimple) {
        String id = resultSimple.get_id();
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = Update.update("dMgP",resultSimple.getDMgP());
        mongoTemplate.upsert(query,update, ResultSimple.class,"resultSimple");
    }

    public void updateALSP(ResultSimple resultSimple) {
        String id = resultSimple.get_id();
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = Update.update("aLSP",resultSimple.getALSP());
        mongoTemplate.upsert(query,update, ResultSimple.class,"resultSimple");
    }

    public ResultSimple find(String id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        ResultSimple resultSimple = mongoTemplate.findOne(query, ResultSimple.class, "resultSimple");
        return resultSimple;
    }

    public void update(ResultSimple resultSimple) {
        Logger logger = LoggerFactory.getLogger("cn.edu.cup.hilly.dataSource.service.mongo.result.ResultSimpleService");
        logger.info("执行了ResultSimpleService的update...");
        String id = resultSimple.get_id();
        Query query = Query.query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query,ResultSimple.class, "resultSimple");
        resultSimpleDao.save(resultSimple);
    }

    public HashMap<String, List<double[]>> findPigV(String id) {
        HashMap<String, List<double[]>> results = new HashMap<>();

        List<Station> stations = stationService.getStationList(id);
        ResultSimple resultSimple = find(id);
        double[][] pigV = resultSimple.getPigV();
        double[][] pigL = resultSimple.getPigL();

        int stationNumb = 0;

        Station startStation = new Station();
        String startStationName = "start";
        double startStationMileage = 0.0;
        Station endStation = new Station();
        String endStationName = "end";
        double endStationMileage = 0.0;
        String between = startStationName + "-" + endStationName;
        List<double[]> result = new ArrayList<>();

        for (int i = 0; i < pigV.length; i++) {
            double time = pigV[i][0];
            double speed = pigV[i][1];
            double mileage = pigL[i][1];


            if (i == 0) {
                startStation = stations.get(stationNumb);
                startStationName = startStation.getStationName().getValue();
                startStationMileage = Double.parseDouble(startStation.getStationL().getValue());
                endStation = stations.get(stationNumb+1);
                endStationName = endStation.getStationName().getValue();
                endStationMileage = Double.parseDouble(endStation.getStationL().getValue());
                between = startStationName + "-" + endStationName;
            }

            if (startStationMileage <= mileage && endStationMileage >= mileage) {
                result.add(pigV[i]);
                results.put(between,result);
            } else if (endStationMileage < mileage) {
                stationNumb ++;
                if (stationNumb == stations.size() - 1) {
                    break;
                }
                startStation = stations.get(stationNumb);
                startStationName = startStation.getStationName().getValue();
                startStationMileage = Double.parseDouble(startStation.getStationL().getValue());
                endStation = stations.get(stationNumb+1);
                endStationName = endStation.getStationName().getValue();
                endStationMileage = Double.parseDouble(endStation.getStationL().getValue());
                between = startStationName + "-" + endStationName;

                result = new ArrayList<>();
            }

        }
        return results;
    }
}
