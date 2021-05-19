package cn.edu.cup.hilly.dataSource.service.mongo;

import cn.edu.cup.hilly.dataSource.mapper.mongo.HillyDao;
import cn.edu.cup.hilly.dataSource.mapper.mongo.MediumListDao;
import cn.edu.cup.hilly.dataSource.model.mongo.Hilly;
import cn.edu.cup.hilly.dataSource.model.mongo.mediumList.Medium;
import cn.edu.cup.hilly.dataSource.model.mongo.mediumList.MediumId;
import cn.edu.cup.hilly.dataSource.model.mongo.mediumList.MediumList;
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

@Service
public class HillyLiquidService {
    @Autowired
    HillyDao hillyDao;
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    MediumListDao mediumListDao;

    /**
     * 快速返回一个未经转换的对象
     * @param id
     * @return
     */
    public JSONObject getMediumListObjById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        BasicDBObject hilly = mongoTemplate.find(query, BasicDBObject.class, "hilly").get(0);
        Object mediumListOld = hilly.get("mediumList");
        String string = JSON.toJSONString(mediumListOld);
        JSONObject json = JSONObject.parseObject(string);
        return json;
    }

    /**
     * 返回一个经过转换的List<Medium>
     * @param id
     * @return
     */
    public List<Medium> getMediumList(String id) {
        JSONObject json = getMediumListObjById(id);
        Object mediums = json.get("value");
        String mediumsString = JSON.toJSONString(mediums);
        List<Medium> mediumsList = JSONObject.parseArray(mediumsString,Medium.class);
        return mediumsList;
    }

    /**
     * 根据项目id和流体id,获取 medium
     * @param hid
     * @param mid
     * @return
     */
    public Medium getMediumById(String hid, String mid) {
        List<Medium> list = getMediumList(hid);
        for (Medium medium : list) {
            if (medium.getMediumId().getValue().equals(mid))
                return medium;
        }
        return null;
    }

    /**
     * 插入一个流体参数
     * @param hid
     * @param medium
     * @return
     */
    public Long insert(String hid, Medium medium) {
        medium.setMediumId(new MediumId());
        List<Medium> list = getMediumList(hid);
        list.add(medium);
        MediumList mediumList = new MediumList();
        mediumList.setValue(list);
        /**
         * 再更新整个hilly中的MediumList
         */
        Query query = new Query(Criteria.where("_id").is(hid));
        Update update = new Update();
        update.set("mediumList",mediumList);
        UpdateResult updateFirst = mongoTemplate.updateFirst(query, update, Hilly.class, "hilly");
        return updateFirst.getModifiedCount();
    }

    /**
     * 更新一条流体数据
     * @param hid
     * @param medium
     * @return
     */
    public long update(String hid, Medium medium) {
        String id = medium.getMediumId().getValue();
        /**
         * 首先删除medium
         */
        List<Medium> values = getMediumList(hid);
        for (Medium valve : values) {
            String s = valve.getMediumId().getValue();
            if (s.equals(id)) {
                values.remove(valve);
                break;
            }
        }
        /**
         * 然后将更新后的medium添加进去
         */
        values.add(medium);
        MediumList mediumList = new MediumList();
        mediumList.setValue(values);
        /**
         * 再更新整个hilly中的MediumList
         */
        Query query = new Query(Criteria.where("_id").is(hid));
        Update update = new Update();
        update.set("mediumList",mediumList);
        UpdateResult updateFirst = mongoTemplate.updateFirst(query, update, Hilly.class, "hilly");
        return updateFirst.getModifiedCount();
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
        List<Medium> value = getMediumList(hid);
        for (Medium medium : value) {
            String s = medium.getMediumId().getValue();
            if (s.equals(id)) {
                value.remove(medium);
                break;
            }
        }
        MediumList mediumList = new MediumList();
        mediumList.setValue(value);
        /**
         * 再更新整个hilly中的MediumList
         */
        Query query = new Query(Criteria.where("_id").is(hid));
        Update update = new Update();
        update.set("mediumList",mediumList);
        UpdateResult updateFirst = mongoTemplate.updateFirst(query, update, Hilly.class, "hilly");
        return updateFirst.getModifiedCount();
    }
}
