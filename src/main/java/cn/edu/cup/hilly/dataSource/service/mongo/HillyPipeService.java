package cn.edu.cup.hilly.dataSource.service.mongo;

import cn.edu.cup.hilly.dataSource.mapper.mongo.HillyDao;
import cn.edu.cup.hilly.dataSource.model.mongo.Hilly;
import cn.edu.cup.hilly.dataSource.model.mongo.Pipe.Pipe;
import cn.edu.cup.hilly.dataSource.model.mongo.pigList.PigList;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * @author wong
 * @date 2021年04月29日 14:28
 */
@Service
public class HillyPipeService {

    @Autowired
    HillyDao hillyDao;
    @Autowired
    MongoTemplate mongoTemplate;

    public JSONObject getById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        BasicDBObject hilly = mongoTemplate.find(query, BasicDBObject.class, "hilly").get(0);
        Object pipeOld = hilly.get("pipe");
        String string = JSON.toJSONString(pipeOld);
        JSONObject json = JSONObject.parseObject(string);
        return json;
    }

    public long update(String id, Pipe pipe) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set("pipe",pipe);
        UpdateResult updateFirst = mongoTemplate.updateFirst(query, update, Hilly.class, "hilly");
        return updateFirst.getModifiedCount();
    }
}
