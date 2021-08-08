package cn.edu.cup.hilly.dataSource.service.mongo;

import cn.edu.cup.hilly.dataSource.mapper.mongo.HillyDao;
import cn.edu.cup.hilly.dataSource.model.mongo.Hilly;
import cn.edu.cup.hilly.dataSource.model.mongo.mediumList.Medium;
import cn.edu.cup.hilly.dataSource.model.mongo.mediumList.MediumId;
import cn.edu.cup.hilly.dataSource.model.mongo.mediumList.MediumList;
import cn.edu.cup.hilly.dataSource.model.mongo.pigList.Pig;
import cn.edu.cup.hilly.dataSource.model.mongo.pigList.PigId;
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

import java.util.Comparator;
import java.util.List;

/**
 * @author wong
 * @date 2021年04月29日 21:41
 */
@Service
public class HillyPigService {
    @Autowired
    HillyDao hillyDao;
    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * 快速返回一个未经转换的对象
     * @param id
     * @return
     */
    public JSONObject getAll(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        BasicDBObject hilly = mongoTemplate.find(query, BasicDBObject.class, "hilly").get(0);
        Object pigListOld = hilly.get("pigList");
        String string = JSON.toJSONString(pigListOld);
        JSONObject json = JSONObject.parseObject(string);
        return json;
    }

    /**
     * 返回一个经过转换的List<Pig>
     * @param id
     * @return
     */
    public List<Pig> getPigList(String id) {
        JSONObject json = getAll(id);
        Object pigs = json.get("value");
        String pigsString = JSON.toJSONString(pigs);
        List<Pig> pigsList = JSONObject.parseArray(pigsString,Pig.class);
        return pigsList;
    }

    /**
     * 根据项目id和流体id,获取 medium
     * @param hid
     * @param pid
     * @return
     */
    public Pig getById(String hid, String pid) {
        List<Pig> list = getPigList(hid);
        for (Pig pig : list) {
            if (pig.getPigId().getValue().equals(pid)){
                return pig;
            }
        }
        return null;
    }

    /**
     * 插入一个流体参数
     * @param hid
     * @param pig
     * @return
     */
    public long insert(String hid, Pig pig) {
        pig.setPigId(new PigId());
        List<Pig> list = getPigList(hid);
        list.add(pig);
        PigList pigList = new PigList();
        pigList.setValue(list);
        /**
         * 再更新整个hilly中的MediumList
         */
        Query query = new Query(Criteria.where("_id").is(hid));
        Update update = new Update();
        update.set("pigList",pigList);
        UpdateResult updateFirst = mongoTemplate.updateFirst(query, update, Hilly.class, "hilly");
        return updateFirst.getModifiedCount();
    }

    /**
     * 更新一条流体数据
     * @param hid
     * @param pig
     * @return
     */
    public long update(String hid, Pig pig) {
        String id = pig.getPigId().getValue();
        /**
         * 首先删除pig
         */
        List<Pig> values = getPigList(hid);
        for (Pig value : values) {
            String s = value.getPigId().getValue();
            if (s.equals(id)) {
                values.remove(value);
                break;
            }
        }
        /**
         * 然后将更新后的medium添加进去
         */
        values.add(pig);
        sortPigs(values);
        PigList pigList = new PigList();
        pigList.setValue(values);
        /**
         * 再更新整个hilly中的MediumList
         */
        Query query = new Query(Criteria.where("_id").is(hid));
        Update update = new Update();
        update.set("pigList",pigList);
        UpdateResult updateFirst = mongoTemplate.updateFirst(query, update, Hilly.class, "hilly");
        return updateFirst.getModifiedCount();
    }
    /**
     * 对全部的清管器进行排序
     * @param values
     */
    private void sortPigs(List<Pig> values) {
        values.sort(new Comparator<Pig>() {
            @Override
            public int compare(Pig medium1, Pig medium2) {
                String string1 = medium1.getPigId().getValue();
                String string2 = medium2.getPigId().getValue();
                return string1.compareTo(string2);
            }
        });
    }

    /**
     * 删除一个流体参数数据
     * @param hid
     * @param id
     * @return
     */
    public long delete(String hid, String id) {
        /**
         * 首先获取到需要删除的medium
         */
        List<Pig> value = getPigList(hid);
        for (Pig pig : value) {
            String s = pig.getPigId().getValue();
            if (s.equals(id)) {
                value.remove(pig);
                break;
            }
        }
        PigList pigList = new PigList();
        pigList.setValue(value);
        /**
         * 再更新整个hilly中的MediumList
         */
        Query query = new Query(Criteria.where("_id").is(hid));
        Update update = new Update();
        update.set("pigList",pigList);
        UpdateResult updateFirst = mongoTemplate.updateFirst(query, update, Hilly.class, "hilly");
        return updateFirst.getModifiedCount();
    }

}
