package cn.edu.cup.hilly.dataSource.service.mongo;

import cn.edu.cup.hilly.dataSource.mapper.mongo.HillyDao;
import cn.edu.cup.hilly.dataSource.model.mongo.Hilly;
import cn.edu.cup.hilly.dataSource.model.mongo.mediumList.Medium;
import cn.edu.cup.hilly.dataSource.model.mongo.mediumList.MediumId;
import cn.edu.cup.hilly.dataSource.model.mongo.mediumList.MediumList;
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

    public MediumList getMediumListById(String id) {
        Hilly hilly = hillyDao.findById(id).get();
        MediumList mediumList = hilly.getMediumList();
        return mediumList;
    }

    public Medium getMediumById(String hid, String id) {
        MediumList mediumListById = getMediumListById(hid);
        List<Medium> value = mediumListById.getValue();
        for (Medium medium : value) {
            String s = medium.getMediumId().getValue();
            if (s.equals(id)) {
                return medium;
            }
        }
        return null;
    }

    public long add(String hid, Medium medium) {
        /**
         * 首先将新的medium添加到MediumList中
         */
        MediumList mediumListById = getMediumListById(hid);
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

    public long update(String hid, Medium medium) {
        /**
         * 首先删除对应id的medium
         */
        String mediumId = medium.getMediumId().getValue();
        delete(hid, mediumId);
        /**
         * 然后将更新后的medium添加进去
         */
        return add(hid, medium);
    }

    public long delete(String hid, String id) {
        /**
         * 首先获取到需要删除的medium
         */
        MediumList mediumListById = getMediumListById(hid);
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
