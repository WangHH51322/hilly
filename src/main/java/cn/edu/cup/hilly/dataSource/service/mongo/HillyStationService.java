package cn.edu.cup.hilly.dataSource.service.mongo;

import cn.edu.cup.hilly.dataSource.mapper.mongo.HillyDao;
import cn.edu.cup.hilly.dataSource.model.mongo.Hilly;
import cn.edu.cup.hilly.dataSource.model.mongo.pumpList.Pump;
import cn.edu.cup.hilly.dataSource.model.mongo.pumpList.PumpId;
import cn.edu.cup.hilly.dataSource.model.mongo.stationList.*;
import cn.edu.cup.hilly.dataSource.model.mongo.valveList.Valve;
import cn.edu.cup.hilly.dataSource.model.mongo.valveList.ValveId;
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

    /**
     * 新建站点
     * @param id
     * @param station
     * @return
     */
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

    /**
     * 修改站点基础信息
     * @param id
     * @param station
     * @return
     */
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
                station.setStationValves(station1.getStationValves());
                station.setStationPumps(station1.getStationPumps());
                stations.remove(station1);
                stations.add(station);
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
     * 获取站内泵列表
     * @param hid
     * @param id
     * @return
     */
    public StationPumps sPGetAllPumps(String hid, String id) {
        Station station = getById(hid, id);
        StationPumps stationPumps = station.getStationPumps();
        return stationPumps;
    }
    public List<Pump> sPGetAll(String hid, String id) {
        Station station = getById(hid, id);
        List<Pump> pumps = station.getStationPumps().getValue();
        return pumps;
    }

    /**
     * 添加单个站内泵
     * @param hid
     * @param id
     * @param pump
     * @return
     */
    public long sPInsert(String hid, String id, Pump pump) {
        /**
         * 首先将pump添加至station中
         */
        pump.setPumpId(new PumpId());
        List<Pump> pumps = sPGetAll(hid, id);
        pumps.add(pump);
        /**
         * 更新stationList中的station
         */
        StationList stationList = getAll(hid);
        List<Station> stations = stationList.getValue();
        for (int i = 0; i < stations.size(); i++) {
            Station station = stations.get(i);
            String stationId = station.getStationId().getValue();
            if (stationId.equals(id)) {
                station.getStationPumps().setValue(pumps);
                stations.set(i,station);
                break;
            }
        }
        stationList.setValue(stations);
        /**
         * 更新stationList
         */
        return extracted(hid, stationList);
    }

    /**
     * 更新站内泵
     * @param hid
     * @param id
     * @param pump
     * @return
     */
    public long sPUpdate(String hid, String id, Pump pump) {
        String pid = pump.getPumpId().getValue();
        /**
         * 删去原有的pump
         */
        sPDelete(hid,id,pid);
        return sPInsert(hid,id,pump);
    }

    /**
     * 删去一个站内pump
     * @param hid
     * @param id
     * @param pid
     * @return
     */
    public long sPDelete(String hid, String id, String pid) {
        /**
         * 删除pump
         */
        List<Pump> pumps = sPGetAll(hid, id);
        for (Pump pump1 : pumps) {
            if (pump1.getPumpId().getValue().equals(pid)) {
                pumps.remove(pump1);
                break;
            }
        }
        /**
         * 更新stationList
         */
        StationList stationList = getAll(hid);
        List<Station> stations = stationList.getValue();
        for (int i = 0; i < stations.size(); i++) {
            Station station = stations.get(i);
            String stationId = station.getStationId().getValue();
            if (stationId.equals(id)) {
                station.getStationPumps().setValue(pumps);
                stations.set(i,station);
                break;
            }
        }
        stationList.setValue(stations);
        /**
         * 更新stationList
         */
        return extracted(hid, stationList);
    }
    /**
     * 获取站内阀列表
     * @param hid
     * @param id
     * @return
     */
    public StationValves sVGetAll(String hid, String id) {
        Station station = getById(hid, id);
        return station.getStationValves();
    }
    /**
     * 添加单个站内阀
     * @param hid
     * @param id
     * @param valve
     * @return
     */
    public long sVInsert(String hid, String id, Valve valve) {
        /**
         * 将valve添加至stationValves
         */
        valve.setValveId(new ValveId());
        StationValves stationValves = sVGetAll(hid, id);
        List<Valve> valves = stationValves.getValue();
        valves.add(valve);
        /**
         * 更新stationList中的station
         */
        StationList stationList = getAll(hid);
        List<Station> stations = stationList.getValue();
        for (int i = 0; i < stations.size(); i++) {
            Station station = stations.get(i);
            String stationId = station.getStationId().getValue();
            if (stationId.equals(id)) {
                station.getStationValves().setValue(valves);
                stations.set(i,station);
                break;
            }
        }
        stationList.setValue(stations);
        /**
         * 更新stationList
         */
        return extracted(hid, stationList);
    }
    /**
     * 根据项目id,站点id,更新后的valve,修改valve
     * @param hid
     * @param id
     * @param valve
     * @return
     */
    public long sVUpdate(String hid, String id, Valve valve) {
        String vid = valve.getValveId().getValue();
        /**
         * 删去原有的valve
         */
        sVDelete(hid,id,vid);
        return sVInsert(hid,id,valve);
    }
    /**
     * 删去一个站内pump
     * @param hid
     * @param id
     * @param vid
     * @return
     */
    public long sVDelete(String hid, String id, String vid) {
        /**
         * 删除pump
         */
        StationValves stationValves = sVGetAll(hid, id);
        List<Valve> valves = stationValves.getValue();
        for (Valve valve : valves) {
            if (valve.getValveId().getValue().equals(vid)) {
                valves.remove(valve);
                break;
            }
        }
        /**
         * 更新stationList
         */
        StationList stationList = getAll(hid);
        List<Station> stations = stationList.getValue();
        for (int i = 0; i < stations.size(); i++) {
            Station station = stations.get(i);
            String stationId = station.getStationId().getValue();
            if (stationId.equals(id)) {
                station.getStationValves().setValue(valves);
                stations.set(i,station);
                break;
            }
        }
        stationList.setValue(stations);
        /**
         * 更新stationList
         */
        return extracted(hid, stationList);
    }
    /**
     * 删除一个站
     */
    public long delete(String hid, String id) {
        /**
         * 首先删去对应的station
         */
        Station station = getById(hid, id);
        StationList stationList = getAll(hid);
        List<Station> value = stationList.getValue();
        value.remove(station);
        stationList.setValue(value);
        /**
         * 更新stationList
         */
        return extracted(hid, stationList);
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
