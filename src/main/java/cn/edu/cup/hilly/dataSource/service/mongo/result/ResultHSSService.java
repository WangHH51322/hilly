package cn.edu.cup.hilly.dataSource.service.mongo.result;

import cn.edu.cup.hilly.dataSource.mapper.mongo.ResultDHLDao;
import cn.edu.cup.hilly.dataSource.mapper.mongo.ResultHSSDao;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultDHL;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultHSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ResultHSSService {

    @Autowired
    ResultHSSDao resultHSSDao;
    @Autowired
    MongoTemplate mongoTemplate;

    public void add(ResultHSS resultHSS) {
        resultHSSDao.save(resultHSS);
    }
    public void update(ResultHSS resultHSS) {
        String id = resultHSS.get_id();
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = Update.update("dHL",resultHSS.getHSS());
        mongoTemplate.upsert(query,update, ResultHSS.class,"resultHSS");
    }
    public void updateMap(ResultHSS resultHSS) {
        String id = resultHSS.get_id();
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = Update.update("hSSMap",resultHSS.getHSSMap());
        mongoTemplate.upsert(query,update, ResultHSS.class,"resultHSS");
    }
    public ResultHSS find(String id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        ResultHSS resultHSS = mongoTemplate.findOne(query, ResultHSS.class, "resultHSS");
        return resultHSS;
    }
}
