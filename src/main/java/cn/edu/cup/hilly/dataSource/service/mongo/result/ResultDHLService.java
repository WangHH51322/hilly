package cn.edu.cup.hilly.dataSource.service.mongo.result;

import cn.edu.cup.hilly.dataSource.mapper.mongo.ResultDHLDao;
import cn.edu.cup.hilly.dataSource.mapper.mongo.ResultDPLDao;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultDHL;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultDPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

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
        String id = resultDHL.get_id();
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = Update.update("dHLMap",resultDHL.getDHLMap());
        System.out.println();
        mongoTemplate.upsert(query,update, ResultDHL.class,"resultDHL");
    }
    public ResultDHL find(String id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        ResultDHL resultDHL = mongoTemplate.findOne(query, ResultDHL.class, "resultDHL");
        return resultDHL;
    }
}
