package cn.edu.cup.hilly.dataSource.service.mongo;

import cn.edu.cup.hilly.dataSource.mapper.mongo.HillyProjectDao;
import cn.edu.cup.hilly.dataSource.model.mongo.Hilly;
import cn.edu.cup.hilly.dataSource.model.mongo.project.HillyProject;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class HillyProjectService {

    @Autowired
    HillyProjectDao hillyProjectDao;
    @Autowired
    HillyService hillyService;
    @Autowired
    MongoTemplate mongoTemplate;

    public List<HillyProject> getAll() {
        return hillyProjectDao.findAll();
    }

    public HillyProject getById(String id) {
        HillyProject hillyProject = hillyProjectDao.findById(id).get();
        return hillyProject;
    }

    public HillyProject add(HillyProject hillyProject) {
        Hilly hilly = new Hilly();
        hilly.setName(hillyProject.getProjectName());
        String _id = hillyService.addHilly(hilly);    //新建hilly对象并返回他的id
        hillyProject.setCreateDate(new Date());
        hillyProject.setChangeDate(new Date());
        hillyProject.setHillyId(_id);   //给hillyProject对象的hillyId属性赋值
        HillyProject save = hillyProjectDao.save(hillyProject);
        return save;
    }

    public long update(HillyProject hillyProject) {
        String id = hillyProject.get_id();
        String hillyId = hillyProject.getHillyId();
        /**
         * 首先修改项目信息表中的数据
         */
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set("projectName",hillyProject.getProjectName());
        update.set("changeDate",new Date());
        update.set("author",hillyProject.getAuthor());
        update.set("remarks",hillyProject.getRemarks());
        UpdateResult updateFirst = mongoTemplate.updateFirst(query, update, HillyProject.class, "hillyProject");
        long matchedCount = updateFirst.getMatchedCount();
        /**
         * 在修改计算数据表中对应的数据
         */
        Query query2 = new Query(Criteria.where("_id").is(hillyId));
        Update update2 = new Update();
        update2.set("name",hillyProject.getProjectName());
        mongoTemplate.updateFirst(query2, update2, Hilly.class, "hilly");
        return matchedCount;
    }

    public long delete(String id) {
        HillyProject hillyProject = hillyProjectDao.findById(id).get();
        String hillyId = hillyProject.getHillyId();
        /**
         * 首先删除项目信息表中的数据
         */
        Query query = new Query(Criteria.where("_id").is(id));
        DeleteResult deleteResult = mongoTemplate.remove(query, HillyProject.class, "hillyProject");
        long deletedCount = deleteResult.getDeletedCount();
        /**
         * 再删除计算参数表中的数据
         */
        Query query2 = new Query(Criteria.where("_id").is(hillyId));
        mongoTemplate.remove(query2, Hilly.class, "hilly");

        return deletedCount;
    }
}
