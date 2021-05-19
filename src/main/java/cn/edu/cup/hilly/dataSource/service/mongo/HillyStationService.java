package cn.edu.cup.hilly.dataSource.service.mongo;

import cn.edu.cup.hilly.dataSource.mapper.mongo.HillyDao;
import cn.edu.cup.hilly.dataSource.model.mongo.Hilly;
import cn.edu.cup.hilly.dataSource.model.mongo.mediumList.Medium;
import cn.edu.cup.hilly.dataSource.model.mongo.mediumList.MediumList;
import cn.edu.cup.hilly.dataSource.model.mongo.pumpList.Pump;
import cn.edu.cup.hilly.dataSource.model.mongo.pumpList.PumpId;
import cn.edu.cup.hilly.dataSource.model.mongo.stationList.*;
import cn.edu.cup.hilly.dataSource.model.mongo.valveList.Valve;
import cn.edu.cup.hilly.dataSource.model.mongo.valveList.ValveId;
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

import java.lang.reflect.Type;
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

    /**
     * 快速返回一个未经转换的对象
     * @param id
     * @return
     */
    public JSONObject getAllStationList(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        BasicDBObject hilly = mongoTemplate.find(query, BasicDBObject.class, "hilly").get(0);
        Object stationListOld = hilly.get("stationList");
        String string = JSON.toJSONString(stationListOld);
        JSONObject json = JSONObject.parseObject(string);
        return json;
    }

    /**
     * 返回一个经过转换的List<Station>
     * @param id
     * @return
     */
    public List<Station> getStationList(String id) {
        JSONObject json = getAllStationList(id);
        Object stations = json.get("value");
        String stationsString = JSON.toJSONString(stations);
        List<Station> stationsList = JSONObject.parseArray(stationsString,Station.class);
        return stationsList;
    }

    public StationList getAll(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        BasicDBObject hilly = mongoTemplate.find(query, BasicDBObject.class, "hilly").get(0);
        Object stationListOld = hilly.get("stationList");
        String string = JSON.toJSONString(stationListOld);
        StationList stations = JSONObject.parseObject(string, StationList.class);
        return stations;
    }

    /**
     * 查询一个站点信息
     * @param hid
     * @param id
     * @return
     */
    public Station getById(String hid, String id) {
        List<Station> stations = getStationList(hid);
        for (Station station : stations) {
            String stationId = station.getStationId().getValue();
            if (stationId.equals(id)) {
                StationPumps stationPump = getStationPump(hid, id);
                StationValves stationValve = getStationValve(hid, id);
                station.setStationPumps(stationPump);
                station.setStationValves(stationValve);
                return station;
            }
        }
        return null;
    }

    /**
     * 新增一个站内泵机组
     * @param
     * @return
     */
    public StationPumps insertStationPump(String hid,String sid) {
        StationPumps stationPumps = new StationPumps();
        stationPumps.setHillyId(hid);
        stationPumps.setStationId(sid);
        StationPumps pumps = mongoTemplate.save(stationPumps, "stationPumps");
        return pumps;
    }
    /**
     * 新增一个站内泵
     * @param
     * @return
     */
    public Long addStationPump(String hid,String sid,Pump pump) {
        Query query = new Query(Criteria.where("hillyId").is(hid).and("stationId").is(sid));
        Update update = new Update();
        pump.setPumpId(new PumpId());
        update.addToSet("value",pump);
        UpdateResult updateFirst = mongoTemplate.updateFirst(query, update, StationPumps.class, "stationPumps");
        return updateFirst.getModifiedCount();
    }

    /**
     * 修改一个站内泵
     * @param hid
     * @param sid
     * @param pump1
     * @return
     */
    public Long updateStationPump(String hid,String sid,Pump pump1) {
        StationPumps stationPump = getStationPump(hid, sid);
        List<Pump> pumps = stationPump.getValue();
        for (Pump pump : pumps) {
            if (pump.getPumpId().getValue().equals(pump1.getPumpId().getValue())) {
                pumps.remove(pump);
                pumps.add(pump1);
                break;
            }
        }
        Query query = new Query(Criteria.where("hillyId").is(hid).and("stationId").is(sid));
        Update update = new Update();
        update.set("value",pumps);
        UpdateResult updateFirst = mongoTemplate.updateFirst(query, update, StationPumps.class, "stationPumps");
        return updateFirst.getModifiedCount();
    }

    /**
     * 删除一个站内泵
     * @param hid
     * @param sid
     * @return
     */
    public Long deleteStationPump(String hid,String sid,String pid) {
        StationPumps stationPump = getStationPump(hid, sid);
        List<Pump> pumps = stationPump.getValue();
        for (Pump pump : pumps) {
            if (pump.getPumpId().getValue().equals(pid)) {
                pumps.remove(pump);
                break;
            }
        }
        Query query = new Query(Criteria.where("hillyId").is(hid).and("stationId").is(sid));
        Update update = new Update();
        update.set("value",pumps);
        UpdateResult updateFirst = mongoTemplate.updateFirst(query, update, StationPumps.class, "stationPumps");
        return updateFirst.getModifiedCount();
    }
    /**
     * 快速返回一个转换的站内泵机组
     * @param id
     * @return
     */
    public StationPumps getStationPump(String id, String sid) {
        Query query = new Query(Criteria.where("hillyId").is(id).and("stationId").is(sid));
        BasicDBObject stationPumps = mongoTemplate.find(query, BasicDBObject.class, "stationPumps").get(0);
        String string = JSON.toJSONString(stationPumps);
        StationPumps json = JSONObject.parseObject(string, StationPumps.class);
        return json;
    }



    /**
     * 新增一个站内阀组
     */
    public StationValves insertStationValve(String hid,String sid) {
        StationValves stationValves = new StationValves();
        stationValves.setHillyId(hid);
        stationValves.setStationId(sid);
        StationValves valves = mongoTemplate.save(stationValves, "stationValves");
        return valves;
    }
    /**
     * 快速返回一个转换的站内阀组
     * @param id
     * @return
     */
    public StationValves getStationValve(String id, String sid) {
        Query query = new Query(Criteria.where("hillyId").is(id).and("stationId").is(sid));
        BasicDBObject stationValves = mongoTemplate.find(query, BasicDBObject.class, "stationValves").get(0);
        String string = JSON.toJSONString(stationValves);
        StationValves json = JSONObject.parseObject(string, StationValves.class);
        return json;
    }
    /**
     * 新增一个站内阀
     * @param
     * @return
     */
    public Long addStationValve(String hid,String sid,Valve valve) {
        Query query = new Query(Criteria.where("hillyId").is(hid).and("stationId").is(sid));
        Update update = new Update();
        valve.setValveId(new ValveId());
        update.addToSet("value",valve);
        UpdateResult updateFirst = mongoTemplate.updateFirst(query, update, StationValves.class, "stationValves");
        return updateFirst.getModifiedCount();
    }
    /**
     * 修改一个站内阀
     * @param hid
     * @param sid
     * @return
     */
    public Long updateStationValve(String hid,String sid,Valve valve1) {
        StationValves stationValve = getStationValve(hid, sid);
        List<Valve> valves = stationValve.getValue();
        for (Valve valve : valves) {
            if (valve.getValveId().getValue().equals(valve1.getValveId().getValue())) {
                valves.remove(valve);
                valves.add(valve1);
                break;
            }
        }
        Query query = new Query(Criteria.where("hillyId").is(hid).and("stationId").is(sid));
        Update update = new Update();
        update.set("value",valves);
        UpdateResult updateFirst = mongoTemplate.updateFirst(query, update, StationValves.class, "stationValves");
        return updateFirst.getModifiedCount();
    }
    /**
     * 删除一个站内泵
     * @param hid
     * @param sid
     * @return
     */
    public Long deleteStationValve(String hid,String sid,String vid) {
        StationValves stationValve = getStationValve(hid, sid);
        List<Valve> valves = stationValve.getValue();
        for (Valve valve : valves) {
            if (valve.getValveId().getValue().equals(vid)) {
                valves.remove(valve);
                break;
            }
        }
        Query query = new Query(Criteria.where("hillyId").is(hid).and("stationId").is(sid));
        Update update = new Update();
        update.set("value",valves);
        UpdateResult updateFirst = mongoTemplate.updateFirst(query, update, StationValves.class, "stationValves");
        return updateFirst.getModifiedCount();
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
        String sid = station.getStationId().getValue();
        insertStationPump(id,sid);
        insertStationValve(id,sid);
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
        List<Station> stations = getStationList(hid);
        for (Station station : stations) {
            if (station.getStationId().getValue().equals(id)) {
                stations.remove(station);
                break;
            }
        }
        StationList stationList = new StationList();
        stationList.setValue(stations);
        /**
         * 删去泵和阀
         */
        Query query = new Query(Criteria.where("hillyId").is(hid).and("stationId").is(id));
        mongoTemplate.remove(query,StationPumps.class,"stationPumps");
        mongoTemplate.remove(query,StationValves.class,"stationValves");
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
        UpdateResult updateFirst = mongoTemplate.updateFirst(query, update, "hilly");
        return updateFirst.getModifiedCount();
    }
}
