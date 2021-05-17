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

    public JSONObject getMediumListObjById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        BasicDBObject hilly = mongoTemplate.find(query, BasicDBObject.class, "hilly").get(0);
        Object mediumListOld = hilly.get("mediumList");
        String string = JSON.toJSONString(mediumListOld);
        JSONObject json = JSONObject.parseObject(string);
        return json;
    }

    public List<Medium> getMediumList(String id) {
        JSONObject json = getMediumListObjById(id);
        Object mediums = json.get("value");
        String mediumsString = JSON.toJSONString(mediums);
        List<Medium> mediumsList = JSONObject.parseArray(mediumsString,Medium.class);
        return mediumsList;
    }

    public MediumList getMediumListById2(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        BasicDBObject hilly = mongoTemplate.find(query, BasicDBObject.class, "hilly").get(0);
        Object mediumListOld = hilly.get("mediumList");
        String string = JSON.toJSONString(mediumListOld);
        JSONObject json = JSONObject.parseObject(string);
        MediumList mediumList = JSON.toJavaObject(json,MediumList.class);
        return mediumList;
    }

    /**
     * 新 getMediumListById
     * @param id
     * @return
     */
    public MediumList getMediumListByIdNew(String id) {
        Query query = new Query(Criteria.where("hillyId").is(id));
        MediumList mediumList = mongoTemplate.find(query, MediumList.class, "mediumList").get(0);
        return mediumList;
    }
    /**
     * 新 getMediumListById
     * @param id
     * @return
     */
    public List<MediumList> getMediumListByIdNewAll(String id) {
        Query query = new Query(Criteria.where("hillyId").is(id));
        List<MediumList> mediumList = mongoTemplate.find(query, MediumList.class, "mediumList");
        return mediumList;
    }

    public Medium getMediumById(String hid, String id) {
        MediumList mediumListById = getMediumListById2(hid);
        List<Medium> value = mediumListById.getValue();
        for (Medium medium : value) {
            String s = medium.getMediumId().getValue();
            if (s.equals(id)) {
                return medium;
            }
        }
        return null;
    }

    /**
     * 新 getMediumById
     * @param hid
     * @param id
     * @return
     */
    public Medium getMediumByIdNew(String hid, String id) {
        MediumList mediumListById = getMediumListByIdNew(hid);
        List<Medium> value = mediumListById.getValue();
        for (Medium medium : value) {
            String s = medium.getMediumId().getValue();
            if (s.equals(id)) {
                return medium;
            }
        }
        return null;
    }

//    /**
//     * 新 add
//     * @param id
//     * @return
//     */
//    public MediumList add(String id) {
//        MediumList mediumList = new MediumList();
//        mediumList.setHillyId(id);
//        MediumList save = mediumListDao.save(mediumList);
//        return save;
//    }

    public long insert(String hid, Medium medium) {
        /**
         * 首先将新的medium添加到MediumList中
         */
        MediumList mediumListById = getMediumListById2(hid);
        List<Medium> value = mediumListById.getValue();
        MediumId mediumId = new MediumId();
        medium.setMediumId(mediumId);
        value.add(medium);
        mediumListById.setValue(value);
        /**
         * 再更新整个hilly中的MediumList
         */
        Query query = new Query(Criteria.where("_id").is(hid));
        Update update = new Update();
        update.set("mediumList",mediumListById);
        UpdateResult updateFirst = mongoTemplate.updateFirst(query, update, Hilly.class, "hilly");
        return updateFirst.getModifiedCount();
    }

    /**
     * 新 insert
     * @param hid
     * @param medium
     * @return
     */
    public long insertNew(String hid, Medium medium) {
        /**
         * 首先将新的medium添加到MediumList中
         */
        MediumList mediumListById = getMediumListByIdNew(hid);
        List<Medium> value = mediumListById.getValue();
        MediumId mediumId = new MediumId();
        medium.setMediumId(mediumId);
        value.add(medium);
        /**
         * 再更新整个MediumList中的value
         */
        Query query = new Query(Criteria.where("hillyId").is(hid));
        Update update = new Update();
        update.set("value",value);
        UpdateResult updateFirst = mongoTemplate.updateFirst(query, update, MediumList.class, "mediumList");
        return updateFirst.getModifiedCount();
    }

    public long update(String hid, Medium medium) {
        /**
         * 首先删除对应id的medium
         */
        String mediumId = medium.getMediumId().getValue();
        delete(hid, mediumId);
        /**
         * 然后将更新后的medium添加进去
         */
        return insert(hid, medium);
    }

    public long delete(String hid, String id) {
        /**
         * 首先获取到需要删除的medium
         */
        MediumList mediumListById = getMediumListById2(hid);
        List<Medium> value = mediumListById.getValue();
        for (Medium medium : value) {
            String s = medium.getMediumId().getValue();
            if (s.equals(id)) {
                value.remove(medium);
                break;
            }
        }
        mediumListById.setValue(value);
        /**
         * 再更新整个hilly中的MediumList
         */
        Query query = new Query(Criteria.where("_id").is(hid));
        Update update = new Update();
        update.set("mediumList",mediumListById);
        UpdateResult updateFirst = mongoTemplate.updateFirst(query, update, Hilly.class, "hilly");
        return updateFirst.getModifiedCount();
    }
}
