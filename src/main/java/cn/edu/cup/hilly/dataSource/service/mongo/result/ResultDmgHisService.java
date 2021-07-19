package cn.edu.cup.hilly.dataSource.service.mongo.result;

import cn.edu.cup.hilly.dataSource.mapper.mongo.ResultDmgHisDao;
import cn.edu.cup.hilly.dataSource.mapper.mongo.ResultPgHisDao;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultDmgHis;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultPgHis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ResultDmgHisService {

    @Autowired
    ResultDmgHisDao resultDmgHisDao;
    @Autowired
    MongoTemplate mongoTemplate;

    public void updateMap(ResultDmgHis resultDmgHis) {
        String id = resultDmgHis.get_id();
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = Update.update("dMgHisMap",resultDmgHis.getDMgHisMap());
        mongoTemplate.upsert(query,update, ResultDmgHis.class,"resultDmgHis");
    }
    public ResultDmgHis find(String id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        ResultDmgHis resultDmgHis = mongoTemplate.findOne(query, ResultDmgHis.class,"resultDmgHis");
        return resultDmgHis;
    }

}
