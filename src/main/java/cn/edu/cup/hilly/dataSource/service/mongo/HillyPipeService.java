package cn.edu.cup.hilly.dataSource.service.mongo;

import cn.edu.cup.hilly.dataSource.mapper.mongo.HillyDao;
import cn.edu.cup.hilly.dataSource.model.mongo.Hilly;
import cn.edu.cup.hilly.dataSource.model.mongo.Pipe.Pipe;
import cn.edu.cup.hilly.dataSource.model.mongo.Pipe.PipeId;
import cn.edu.cup.hilly.dataSource.model.mongo.Pipe.PipeList;
import cn.edu.cup.hilly.dataSource.model.mongo.mediumList.Medium;
import cn.edu.cup.hilly.dataSource.model.mongo.mediumList.MediumId;
import cn.edu.cup.hilly.dataSource.model.mongo.mediumList.MediumList;
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

import java.util.List;

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

    public JSONObject getAll(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        BasicDBObject hilly = mongoTemplate.find(query, BasicDBObject.class, "hilly").get(0);
        Object pipeOld = hilly.get("pipeList");
        String string = JSON.toJSONString(pipeOld);
        JSONObject json = JSONObject.parseObject(string);
        return json;
    }

    /**
     * 返回一个经过转换的List<Pipe>
     * @param id
     * @return
     */
    public List<Pipe> getPipeList(String id) {
        JSONObject json = getAll(id);
        Object pipes = json.get("value");
        String pipesString = JSON.toJSONString(pipes);
        List<Pipe> pipesList = JSONObject.parseArray(pipesString,Pipe.class);
        return pipesList;
    }

    /**
     * 根据项目id和流体id,获取 pipe
     * @param hid
     * @param mid
     * @return
     */
    public Pipe getPipeById(String hid, String mid) {
        List<Pipe> list = getPipeList(hid);
        for (Pipe pipe : list) {
            if (pipe.getPipeId().getValue().equals(mid))
                return pipe;
        }
        return null;
    }

    /**
     * 插入一个流体参数
     * @param hid
     * @param pipe
     * @return
     */
    public Long insert(String hid, Pipe pipe) {
        pipe.setPipeId(new PipeId());
        List<Pipe> list = getPipeList(hid);
        list.add(pipe);
        PipeList pipeList = new PipeList();
        pipeList.setValue(list);
        /**
         * 再更新整个hilly中的pipeList
         */
        Query query = new Query(Criteria.where("_id").is(hid));
        Update update = new Update();
        update.set("pipeList",pipeList);
        UpdateResult updateFirst = mongoTemplate.updateFirst(query, update, Hilly.class, "hilly");
        return updateFirst.getModifiedCount();
    }

    /**
     * 更新一条流体数据
     * @param hid
     * @param pipe
     * @return
     */
    public long update(String hid, Pipe pipe) {
        String id = pipe.getPipeId().getValue();
        /**
         * 首先删除pipe
         */
        List<Pipe> values = getPipeList(hid);
        for (Pipe value : values) {
            String s = value.getPipeId().getValue();
            if (s.equals(id)) {
                values.remove(value);
                break;
            }
        }
        /**
         * 然后将更新后的medium添加进去
         */
        values.add(pipe);
        PipeList pipeList = new PipeList();
        pipeList.setValue(values);
        /**
         * 再更新整个hilly中的MediumList
         */
        Query query = new Query(Criteria.where("_id").is(hid));
        Update update = new Update();
        update.set("pipeList",pipeList);
        UpdateResult updateFirst = mongoTemplate.updateFirst(query, update, Hilly.class, "hilly");
        return updateFirst.getModifiedCount();
    }

    /**
     * 删除一个管段参数数据
     * @param hid
     * @param id
     * @return
     */
    public long delete(String hid, String id) {
        /**
         * 首先获取到需要删除的pipe
         */
        List<Pipe> value = getPipeList(hid);
        for (Pipe pipe : value) {
            String s = pipe.getPipeId().getValue();
            if (s.equals(id)) {
                value.remove(pipe);
                break;
            }
        }
        PipeList pipeList = new PipeList();
        pipeList.setValue(value);
        /**
         * 再更新整个hilly中的pipeList
         */
        Query query = new Query(Criteria.where("_id").is(hid));
        Update update = new Update();
        update.set("pipeList",pipeList);
        UpdateResult updateFirst = mongoTemplate.updateFirst(query, update, Hilly.class, "hilly");
        return updateFirst.getModifiedCount();
    }
}
