package cn.edu.cup.hilly.dataSource.service.mongo;

import cn.edu.cup.hilly.dataSource.mapper.mongo.HillyDao;
import cn.edu.cup.hilly.dataSource.model.mongo.Hilly;
import cn.edu.cup.hilly.dataSource.model.mongo.pigList.Pig;
import cn.edu.cup.hilly.dataSource.model.mongo.pigList.PigId;
import cn.edu.cup.hilly.dataSource.model.mongo.pigList.PigList;
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
 * @date 2021年04月29日 21:41
 */
@Service
public class HillyPigService {
    @Autowired
    HillyDao hillyDao;
    @Autowired
    MongoTemplate mongoTemplate;

    public PigList getAll(String id) {
        Hilly hilly = hillyDao.findById(id).get();
        return hilly.getPigList();
    }

    public Pig getById(String hid, String id) {
        PigList pigList = getAll(hid);
        List<Pig> pigs = pigList.getValue();
        for (Pig pig : pigs) {
            String value = pig.getPigId().getValue();
            if (value.equals(id)) {
                return pig;
            }
        }
        return null;
    }

    public long insert(String id, Pig pig) {
        /**
         * 首先将新的pig添加到pigList中
         */
        PigList pigList = getAll(id);
        List<Pig> pigs = pigList.getValue();
        pig.setPigId(new PigId());
        pigs.add(pig);
        pigList.setValue(pigs);
        /**
         * 再更新整个hilly中的pigList
         */
        return extracted(id, pigList);
    }

    public long update(String id, Pig pig) {
        /**
         * 首先删除对应id的pig
         */
        String pigId = pig.getPigId().getValue();
        delete(id,pigId);
        /**
         * 然后将新的pig添加进去
         */
        return insert(id,pig);
    }

    public long delete(String hid, String id) {
        /**
         * 首先将新的pig从pigList删除
         */
        PigList pigList = getAll(hid);
        List<Pig> pigs = pigList.getValue();
        for (Pig pig : pigs) {
            String pigId = pig.getPigId().getValue();
            if (pigId.equals(id)) {
                pigs.remove(pig);
                break;
            }
        }
        pigList.setValue(pigs);
        /**
         * 再更新整个hilly中的pigList
         */
        return extracted(hid, pigList);
    }

    /**
     * 抽离部分代码
     * @param id
     * @param pigList
     * @return
     */
    private long extracted(String id, PigList pigList) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set("pigList", pigList);
        UpdateResult updateFirst = mongoTemplate.updateFirst(query, update, Hilly.class, "hilly");
        return updateFirst.getModifiedCount();
    }

}
