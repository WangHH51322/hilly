package cn.edu.cup.hilly.dataSource.service.mongo.result;

import cn.edu.cup.hilly.dataSource.mapper.mongo.ResultALFPDao;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultAllLineFP;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultDPL;
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
public class ResultALFPService {

    @Autowired
    ResultALFPDao resultALSPDao;
    @Autowired
    MongoTemplate mongoTemplate;

    public void updateMap(ResultAllLineFP resultAllLineFP) {
        String id = resultAllLineFP.get_id();
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = Update.update("allLineFPMap",resultAllLineFP.getALineFPMap());
        mongoTemplate.upsert(query,update, ResultAllLineFP.class,"resultALFP");
    }
}
