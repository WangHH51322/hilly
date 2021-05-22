package cn.edu.cup.hilly.dataSource.service.mongo.result;

import cn.edu.cup.hilly.dataSource.mapper.mongo.ResultMHisDao;
import cn.edu.cup.hilly.dataSource.mapper.mongo.ResultPgHisDao;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultMHis;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultPgHis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ResultMHisService {

    @Autowired
    ResultMHisDao resultMHisDao;
    @Autowired
    MongoTemplate mongoTemplate;

    public void updateMap(ResultMHis resultMHis) {
        String id = resultMHis.get_id();
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = Update.update("mHisMap",resultMHis.getMHisMap());
        mongoTemplate.upsert(query,update, ResultMHis.class,"resultMHis");
    }
    public ResultMHis find(String id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        ResultMHis resultMHis = mongoTemplate.findOne(query, ResultMHis.class, "resultMHis");
        return resultMHis;
    }
}
