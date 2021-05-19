package cn.edu.cup.hilly.dataSource.service.mongo;

import cn.edu.cup.hilly.dataSource.mapper.mongo.HillyDao;
import cn.edu.cup.hilly.dataSource.model.mongo.Hilly;
import cn.edu.cup.hilly.dataSource.model.mongo.variableParameter.VariableParameter;
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

@Service
public class HillyVarParaService {
    @Autowired
    HillyDao hillyDao;
    @Autowired
    MongoTemplate mongoTemplate;

    public JSONObject getById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        BasicDBObject hilly = mongoTemplate.find(query, BasicDBObject.class, "hilly").get(0);
        Object interInfoOld = hilly.get("variableParameter");
        String string = JSON.toJSONString(interInfoOld);
        JSONObject json = JSONObject.parseObject(string);
        return json;
    }

    public long update(String id, VariableParameter variableParameter) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set("variableParameter",variableParameter);
        UpdateResult updateFirst = mongoTemplate.updateFirst(query, update, Hilly.class, "hilly");
        return updateFirst.getModifiedCount();
    }
}
