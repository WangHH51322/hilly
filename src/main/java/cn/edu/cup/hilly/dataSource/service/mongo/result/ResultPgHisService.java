package cn.edu.cup.hilly.dataSource.service.mongo.result;

import cn.edu.cup.hilly.dataSource.mapper.mongo.ResultLgHisDao;
import cn.edu.cup.hilly.dataSource.mapper.mongo.ResultPgHisDao;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultDPL;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultLgHis;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultPgHis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ResultPgHisService {

    @Autowired
    ResultPgHisDao resultPgHisDao;
    @Autowired
    MongoTemplate mongoTemplate;

    public void updateMap(ResultPgHis resultPgHis) {
        String id = resultPgHis.get_id();
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = Update.update("pgHisMap",resultPgHis.getPgHisMap());
        mongoTemplate.upsert(query,update, ResultPgHis.class,"resultPgHis");
    }
    public ResultPgHis find(String id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        ResultPgHis resultPgHis = mongoTemplate.findOne(query, ResultPgHis.class,"resultPgHis");
        return resultPgHis;
    }

}
