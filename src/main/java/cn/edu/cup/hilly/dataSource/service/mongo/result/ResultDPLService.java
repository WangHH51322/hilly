package cn.edu.cup.hilly.dataSource.service.mongo.result;

import cn.edu.cup.hilly.dataSource.mapper.mongo.ResultDPLDao;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultDPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

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
        String id = resultDPL.get_id();
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = Update.update("dPLMap",resultDPL.getDPLMap());
        mongoTemplate.upsert(query,update, ResultDPL.class,"resultDPL");
    }
    public ResultDPL find(String id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        ResultDPL resultDPL = mongoTemplate.findOne(query, ResultDPL.class, "resultDPL");
        return resultDPL;
    }
}
