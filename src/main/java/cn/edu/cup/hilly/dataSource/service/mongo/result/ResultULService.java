package cn.edu.cup.hilly.dataSource.service.mongo.result;

import cn.edu.cup.hilly.dataSource.mapper.mongo.ResultALFPDao;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultAllLineFP;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultULocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * @author CodeChap
 * @date 2021-05-04- 19:22
 */
@Service
public class ResultULService {

    @Autowired
    ResultALFPDao resultALSPDao;
    @Autowired
    MongoTemplate mongoTemplate;

    public void updateMap(ResultULocation resultULocation) {
        String id = resultULocation.getHillyId();
        Query query = Query.query(Criteria.where("hillyId").is(id));
        Update update = Update.update("uLocation",resultULocation.getULocation());
        mongoTemplate.upsert(query,update, ResultULocation.class,"resultULocation");
    }

    public ResultULocation find(String id) {
        Query query = Query.query(Criteria.where("hillyId").is(id));
        ResultULocation resultULocation = mongoTemplate.findOne(query, ResultULocation.class,"resultULocation");
        return resultULocation;
    }
}
