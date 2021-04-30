package cn.edu.cup.hilly.dataSource.service.mongo;

import cn.edu.cup.hilly.dataSource.mapper.mongo.HillyDao;
import cn.edu.cup.hilly.dataSource.model.mongo.Hilly;
import cn.edu.cup.hilly.dataSource.model.mongo.stationList.*;
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
 * @date 2021年04月30日 17:17
 */
@Service
public class HillyStationService {

    @Autowired
    HillyDao hillyDao;
    @Autowired
    MongoTemplate mongoTemplate;

    public StationList getAll(String id) {
        Hilly hilly = hillyDao.findById(id).get();
        return hilly.getStationList();
    }

    public Station getById(String hid, String id) {
        StationList stationList = getAll(hid);
        List<Station> stations = stationList.getValue();
        for (Station station : stations) {
            String stationId = station.getStationId().getValue();
            if (stationId.equals(id)) {
                return station;
            }
        }
        return null;
    }

    public long insert(String id, Station station) {
        /**
         * 将新建的station补充完整并添加进stationList
         */
        StationList stationList = getAll(id);
        List<Station> stations = stationList.getValue();
        station.setStationId(new StationId());
        station.setStationPumps(new StationPumps());
        station.setStationValves(new StationValves());
        stations.add(station);
        stationList.setValue(stations);
        return extracted(id, stationList);
    }

    public long bIUpdate(String id, Station station) {
        /**
         * 只更新stationList中的基础信息部分
         */
        StationList stationList = getAll(id);
        List<Station> stations = stationList.getValue();
        String stationId = station.getStationId().getValue();
        for (Station station1 : stations) {
            String value = station1.getStationId().getValue();
            if (value.equals(stationId)) {
                station1.setStationName(station.getStationName());
                station1.setStationType(station.getStationType());
                station1.setStationL(station.getStationL());
                station1.setStationZ(station.getStationZ());
                break;
            }
        }
        stationList.setValue(stations);
        /**
         * 更新stationList
         */
        return extracted(id, stationList);
    }

    /**
     * 抽取代码块
     * @param id
     * @param stationList
     * @return
     */
    private long extracted(String id, StationList stationList) {
        /**
         * 更新stationList
         */
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set("stationList", stationList);
        UpdateResult updateFirst = mongoTemplate.updateFirst(query, update, Hilly.class, "hilly");
        return updateFirst.getModifiedCount();
    }
}
