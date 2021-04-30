package cn.edu.cup.hilly.dataSource.service.mongo;

import cn.edu.cup.hilly.dataSource.mapper.mongo.HillyDao;
import cn.edu.cup.hilly.dataSource.model.mongo.Hilly;
import cn.edu.cup.hilly.dataSource.model.mongo.valveList.Valve;
import cn.edu.cup.hilly.dataSource.model.mongo.valveList.ValveId;
import cn.edu.cup.hilly.dataSource.model.mongo.valveList.ValveList;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HillyValveService {
    @Autowired
    HillyDao hillyDao;
    @Autowired
    MongoTemplate mongoTemplate;

    public ValveList getAll(String id) {
        Hilly hilly = hillyDao.findById(id).get();
        ValveList valveList = hilly.getValveList();
        return valveList;
    }

    public Valve getById(String hid, String id) {
        ValveList all = getAll(hid);
        List<Valve> valves = all.getValue();
        for (Valve valve : valves) {
            String value = valve.getValveId().getValue();
            if (value.equals(id)) {
                return valve;
            }
        }
        return null;
    }

    public long add(String id, Valve valve) {
        /**
         * 首先将新的valve添加到ValveList中
         */
        ValveList valveList = getAll(id);
        List<Valve> valves = valveList.getValue();
        valve.setValveId(new ValveId());
        valves.add(valve);
        valveList.setValue(valves);
        /**
         * 再更新整个hilly中的ValveList
         */
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set("valveList",valveList);
        UpdateResult updateFirst = mongoTemplate.updateFirst(query, update, Hilly.class, "hilly");
        return updateFirst.getModifiedCount();
    }

    public long update(String id, Valve valve) {
        /**
         * 首先删除对应id的valve
         */
        String valveId = valve.getValveId().getValue();
        delete(id, valveId);
        /**
         * 然后将更新后的valve添加进去
         */
        return add(id, valve);
    }

    public long delete(String hid, String id) {
        /**
         * 首先获取到需要删除的valve
         */
        ValveList valveList = getAll(hid);
        List<Valve> valves = valveList.getValue();
        for (Valve valve : valves) {
            String value = valve.getValveId().getValue();
            if (value.equals(id)) {
                valves.remove(valve);
                break;
            }
        }
        valveList.setValue(valves);
        /**
         * 再更新整个hilly中的ValveList
         */
        Query query = new Query(Criteria.where("_id").is(hid));
        Update update = new Update();
        update.set("valveList",valveList);
        UpdateResult updateFirst = mongoTemplate.updateFirst(query, update, Hilly.class, "hilly");
        return updateFirst.getModifiedCount();
    }
}
