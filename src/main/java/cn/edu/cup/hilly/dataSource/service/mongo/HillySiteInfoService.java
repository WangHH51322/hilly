package cn.edu.cup.hilly.dataSource.service.mongo;

import cn.edu.cup.hilly.dataSource.mapper.mongo.HillyDao;
import cn.edu.cup.hilly.dataSource.model.mongo.Hilly;
import cn.edu.cup.hilly.dataSource.model.mongo.siteInfo.SiteInfo;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class HillySiteInfoService {
    @Autowired
    HillyDao hillyDao;
    @Autowired
    MongoTemplate mongoTemplate;

    public SiteInfo getSiteInfoById(String id) {
        Hilly hilly = hillyDao.findById(id).get();
        SiteInfo siteInfo = hilly.getSiteInfo();
        return siteInfo;
    }

    public long add(String hid, SiteInfo siteInfo) {
        Query query = new Query(Criteria.where("_id").is(hid));
        Update update = new Update();
        update.set("siteInfo",siteInfo);
        UpdateResult hilly = mongoTemplate.upsert(query, update, Hilly.class, "hilly");
        return hilly.getModifiedCount();
    }

    public long delete(String hid) {
        SiteInfo siteInfo = new SiteInfo();
        return add(hid,siteInfo);
    }
}
