package cn.edu.cup.hilly.dataSource.service.mongo.result;

import cn.edu.cup.hilly.dataSource.mapper.mongo.ResultDPLDao;
import cn.edu.cup.hilly.dataSource.mapper.mongo.ResultLgHisDao;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultAllLineFP;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultDPL;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultLgHis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ResultLgHisService {

    @Autowired
    ResultLgHisDao resultLgHisDao;
    @Autowired
    MongoTemplate mongoTemplate;

    public void updateMap(ResultLgHis resultLgHis) {
        String id = resultLgHis.get_id();
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = Update.update("lgHisMap",resultLgHis.getLgHisMap());
        mongoTemplate.upsert(query,update, ResultLgHis.class,"resultLgHis");
    }

    public ResultLgHis find(String id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        ResultLgHis resultLgHis = mongoTemplate.findOne(query, ResultLgHis.class,"resultLgHis");
        return resultLgHis;
    }

}
