package cn.edu.cup.hilly.dataSource.service.mongo;

import cn.edu.cup.hilly.dataSource.mapper.mongo.StationPumpsDao;
import cn.edu.cup.hilly.dataSource.model.mongo.pumpList.Pump;
import cn.edu.cup.hilly.dataSource.model.mongo.pumpList.PumpId;
import cn.edu.cup.hilly.dataSource.model.mongo.stationList.StationPumps;
import com.mongodb.BasicDBObject;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author CodeChap
 * @date 2021-05-16 22:25
 */
@Service
@Transactional
public class HillyStationPumpsService {

    @Autowired
    StationPumpsDao stationPumpsDao;
    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * 新建一个 站点内泵机组
     * @param id
     * @return
     */
    public StationPumps add(String id) {
        StationPumps stationPumps = new StationPumps();
        stationPumps.setHillyId(id);
        StationPumps save = stationPumpsDao.save(stationPumps);
        return save;
    }

    /**
     * 查询并返回某一站点内的泵机组
     * @param id
     * @return
     */
    public BasicDBObject getAll(String id) {
        Query query = new Query(Criteria.where("hillyId").is(id));
//        StationPumps stationPumps = mongoTemplate.find(query, StationPumps.class, "stationPumps").get(0);
        BasicDBObject stationPumps = mongoTemplate.find(query, BasicDBObject.class, "stationPumps").get(0);
        return stationPumps;
    }

    /**
     * 查询并返回站点内的一台泵
     * @param hid
     * @param pid
     * @return
     */
    public Pump getOne(String hid,String pid) {
//        StationPumps stationPumps = getAll(hid);
//        List<Pump> pumps = stationPumps.getValue();
//        for (Pump pump : pumps) {
//            if (pump.getPumpId().getValue().equals(pid)) {
//                return pump;
//            }
//        }
        return null;
    }

    /**
     * 向站内泵机组添加一台泵
     * @param id
     * @param pump
     * @return
     */
    public Long insert(String id,Pump pump) {
        PumpId pumpId = new PumpId();
        pump.setPumpId(pumpId);
        Query query = new Query(Criteria.where("hillyId").is(id));
        Update update = new Update();
        update.addToSet("value",pump);
        UpdateResult stationPumps = mongoTemplate.upsert(query, update, StationPumps.class, "stationPumps");
        long modifiedCount = stationPumps.getModifiedCount();
        return modifiedCount;
    }

//    public Long delete(String id,Pump pump) {
//        String pumpId = pump.getPumpId().getValue();
//
//    }
}
