package cn.edu.cup.hilly.dataSource.model.mongo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 将Hilly对象转换成符合要求的Map<String,Object>格式
 */
public class DataMap {

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
}
