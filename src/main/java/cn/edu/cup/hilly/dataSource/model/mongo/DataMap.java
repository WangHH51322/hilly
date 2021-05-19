package cn.edu.cup.hilly.dataSource.model.mongo;

import cn.edu.cup.hilly.dataSource.model.mongo.stationList.Station;
import cn.edu.cup.hilly.dataSource.model.mongo.stationList.StationList;
import cn.edu.cup.hilly.dataSource.model.mongo.stationList.StationPumps;
import cn.edu.cup.hilly.dataSource.model.mongo.stationList.StationValves;
import cn.edu.cup.hilly.dataSource.service.mongo.HillyService;
import cn.edu.cup.hilly.dataSource.service.mongo.HillyStationService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 将Hilly对象转换成符合要求的Map<String,Object>格式
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class DataMap {

    @Autowired
    HillyService hillyService;
    @Autowired
    HillyStationService hillyStationService;

    private String id;

    public DataMap(String id) {
        this.id = id;
    }

    public Map<String,Object> convertDataMap(String id) {
        BasicDBObject hilly = hillyService.getHillyById2(id);
        StationList stationList = hillyStationService.getAll(id);
        List<Station> stations = stationList.getValue();
        for (Station station : stations) {
            String stationId = station.getStationId().getValue();
            StationPumps stationPump = hillyStationService.getStationPump(id, stationId);
            station.setStationPumps(stationPump);
            StationValves stationValve = hillyStationService.getStationValve(id, stationId);
            station.setStationValves(stationValve);
        }
        stationList.setValue(stations);
        hilly.remove("stationList");
        hilly.append("stationList",stationList);

        Map<String, Object> dataMap = getDataMap(hilly);
        return dataMap;
    }

    public static Map<String,Object> getDataMap(Hilly hilly) {
        /**
         * projectName:获取hilly中的项目名称
         */
        String projectName = "hillyProject";
        /**
         * 将hilly对象转换为String字符串
         */
        String jsonString = JSON.toJSONString(hilly);
        /**
         * 将String字符串转换为Json对象
         */
        JSONObject jsonObject = JSON.parseObject(jsonString);
        /**
         * 移除hilly对象中不需要的"name"字段
         * "_id"字段可能需要保留,用于保存计算的数据时,去数据库寻找对应的document
         */
        jsonObject.remove("name");
        /**
         * 新建Map<String,Object>,并将项目名称projectName作为key,hilly对象中其余的属性值作为value
         */
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put(projectName,jsonObject);

        return dataMap;
    }

    public static Map<String,Object> getDataMap(Object hilly) {
        /**
         * projectName:获取hilly中的项目名称
         */
        String projectName = "hillyProject";
        /**
         * 将hilly对象转换为String字符串
         */
        String jsonString = JSON.toJSONString(hilly);
        /**
         * 将String字符串转换为Json对象
         */
        JSONObject jsonObject = JSON.parseObject(jsonString);
        /**
         * 移除hilly对象中不需要的"name"字段
         * "_id"字段可能需要保留,用于保存计算的数据时,去数据库寻找对应的document
         */
        jsonObject.remove("name");
        /**
         * 新建Map<String,Object>,并将项目名称projectName作为key,hilly对象中其余的属性值作为value
         */
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put(projectName,jsonObject);

        return dataMap;
    }
}
