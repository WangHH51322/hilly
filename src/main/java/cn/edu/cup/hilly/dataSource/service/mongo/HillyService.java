package cn.edu.cup.hilly.dataSource.service.mongo;

import cn.edu.cup.hilly.dataSource.mapper.mongo.HillyDao;
import cn.edu.cup.hilly.dataSource.model.mongo.Hilly;
import cn.edu.cup.hilly.dataSource.model.mongo.Pipe.Pipe;
import cn.edu.cup.hilly.dataSource.model.mongo.interInfo.InterInfo;
import cn.edu.cup.hilly.dataSource.model.mongo.mediumList.Medium;
import cn.edu.cup.hilly.dataSource.model.mongo.mediumList.MediumList;
import cn.edu.cup.hilly.dataSource.model.mongo.pigList.Pig;
import cn.edu.cup.hilly.dataSource.model.mongo.pigList.PigList;
import cn.edu.cup.hilly.dataSource.model.mongo.siteInfo.SiteInfo;
import cn.edu.cup.hilly.dataSource.model.mongo.variableParameter.VariableParameter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class HillyService {

    @Autowired
    HillyDao hillyDao;
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Hilly> getAllHilly() {
        System.out.println("getAllHilly方法已执行");
        List<Hilly> all = hillyDao.findAll();
        return all;
    }

    public Hilly getHillyById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        Hilly hilly = mongoTemplate.find(query, Hilly.class, "hilly").get(0);
//        Hilly hilly = hillyDao.findById(id).get();
        return hilly;
    }

    public BasicDBObject getHillyById2(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        BasicDBObject hilly = mongoTemplate.find(query, BasicDBObject.class, "hilly").get(0);
//        hilly.put("_id", hilly.get("_id"));
//        System.out.println(hilly.get("_id"));
//        String hillyString = JSON.toJSONString(hilly);
//        JSONObject hillyJson = JSONObject.parseObject(hillyString);
//        Hilly hillyNew = JSON.toJavaObject(hillyJson,Hilly.class);
//        String jsonString = JSON.toJSONString(hilly);
//        JSONObject jsonObject = JSON.parseObject(jsonString);
//        Object interInfo = jsonObject.get("interInfo");
//        String interInfoString = JSON.toJSONString(interInfo);
//        JSONObject interInfoObj = JSON.parseObject(interInfoString);
//        Object errorInfo = interInfoObj.get("errorInfo");
        System.out.println(hilly);
//        Hilly hilly = hillyDao.findById(id).get();
        return hilly;
    }

    //新建一个Hilly数据并返回_id
    public String addHilly(Hilly hilly) {
        Hilly insert = hillyDao.insert(hilly);
        String id = insert.get_id();
        return id;
    }

    public SiteInfo getSiteInfoById(String id) {
        Hilly hilly = hillyDao.findById(id).get();
        SiteInfo siteInfo = hilly.getSiteInfo();
        return siteInfo;
    }

    public long updateSiteInfoById(String id, SiteInfo siteInfo) {
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = Update.update("siteInfo",siteInfo);
        long count = mongoTemplate.upsert(query, update, Hilly.class, "hilly").getModifiedCount();
        return count;
    }

    public MediumList getMediumListById(String id) {
        Hilly hilly = hillyDao.findById(id).get();
        MediumList mediumList = hilly.getMediumList();
        return mediumList;
    }

    public long updateMediumListById(String id, MediumList mediumList) {
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = Update.update("mediumList",mediumList);
        long count = mongoTemplate.upsert(query, update, Hilly.class, "hilly").getModifiedCount();
        return count;
    }

    public VariableParameter getVPById(String id) {
        Hilly hilly = hillyDao.findById(id).get();
        VariableParameter variableParameter = hilly.getVariableParameter();
        return variableParameter;
    }

    public long updateVPById(String id, VariableParameter variableParameter) {
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = Update.update("variableParameter",variableParameter);
        long count = mongoTemplate.upsert(query, update, Hilly.class, "hilly").getModifiedCount();
        return count;
    }

    public PigList getPigListById(String id) {
        Hilly hilly = hillyDao.findById(id).get();
        PigList pigList = hilly.getPigList();
        return pigList;
    }

    public long updatePigListById(String id, PigList pigList) {
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = Update.update("pigList",pigList);
        long count = mongoTemplate.upsert(query, update, Hilly.class, "hilly").getModifiedCount();
        return count;
    }

    public Medium addMedium(String id) {
        Hilly hilly = hillyDao.findById(id).get();
        MediumList mediumList = hilly.getMediumList();
        List<Medium> value = mediumList.getValue();
        Medium medium = new Medium();
        value.add(medium);
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = Update.update("mediumList",mediumList);
        mongoTemplate.upsert(query, update, Hilly.class, "hilly");
        return medium;
    }

    public void updateMedium(String id, Medium medium) {
        Hilly hilly = hillyDao.findById(id).get();
        MediumList mediumList = hilly.getMediumList();
        List<Medium> value = mediumList.getValue();
        for (Medium medium1 : value) {
            if(medium1.getMediumId().equals(medium.getMediumId())) {
                value.remove(medium1);
                value.add(medium);
                break;
            }
        }
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = Update.update("mediumList",mediumList);
        mongoTemplate.upsert(query, update, Hilly.class, "hilly");
    }

    public Pig addPig(String id) {
        Hilly hilly = hillyDao.findById(id).get();
        PigList pigList = hilly.getPigList();
        List<Pig> value = pigList.getValue();
        Pig pig = new Pig();
        value.add(pig);
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = Update.update("pigList",pigList);
        mongoTemplate.upsert(query, update, Hilly.class, "hilly");
        return pig;
    }

    public void updatePig(String id, Pig pig) {
        Hilly hilly = hillyDao.findById(id).get();
        PigList pigList = hilly.getPigList();
        List<Pig> value = pigList.getValue();
        for (Pig pig1 : value) {
            if(pig1.getPigId().equals(pig.getPigId())) {
                value.remove(pig1);
                value.add(pig);
                break;
            }
        }
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = Update.update("pigList",pigList);
        mongoTemplate.upsert(query, update, Hilly.class, "hilly");
    }

    public Pipe getPipeById(String id) {
        Hilly hilly = hillyDao.findById(id).get();
        Pipe pipe = hilly.getPipe();
        return pipe;
    }

    public long updatePipeById(String id, Pipe pipe) {
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = Update.update("pipe",pipe);
        long l = mongoTemplate.upsert(query, update, Hilly.class, "hilly").getModifiedCount();
        return l;
    }
}
