package cn.edu.cup.hilly.dataSource.service.mongo.result;

import cn.edu.cup.hilly.dataSource.mapper.mongo.ResultDPLDao;
import cn.edu.cup.hilly.dataSource.mapper.mongo.ResultSimpleDao;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultDPL;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultMHis;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultSimple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ResultSimpleService {

    @Autowired
    ResultSimpleDao resultSimpleDao;
    @Autowired
    MongoTemplate mongoTemplate;

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
        String id = resultSimple.get_id();
        Query query = Query.query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query,ResultSimple.class, "resultSimple");
        resultSimpleDao.save(resultSimple);
    }
}
