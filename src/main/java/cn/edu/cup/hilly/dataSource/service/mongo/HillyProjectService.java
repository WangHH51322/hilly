package cn.edu.cup.hilly.dataSource.service.mongo;

import cn.edu.cup.hilly.dataSource.mapper.mongo.HillyProjectDao;
import cn.edu.cup.hilly.dataSource.model.mongo.Hilly;
import cn.edu.cup.hilly.dataSource.model.mongo.project.HillyProject;
import cn.edu.cup.hilly.dataSource.model.mongo.stationList.StationPumps;
import cn.edu.cup.hilly.dataSource.utils.MongoUtil;
import cn.edu.cup.hilly.dataSource.utils.PageHelper;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class HillyProjectService {

    @Autowired
    HillyProjectDao hillyProjectDao;
    @Autowired
    HillyStationService hillyStationService;
    @Autowired
    HillyService hillyService;
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    MongoUtil mongoUtil;

    public List<HillyProject> getAll() {
        return hillyProjectDao.findAll();
    }

    public List<HillyProject> getAllByUserId(String id) {
        Query query = new Query(Criteria.where("userId").is(id));
        List<HillyProject> hillyProject = mongoTemplate.find(query, HillyProject.class, "hillyProject");
        return hillyProject;
    }

    public HillyProject getById(String id) {
        HillyProject hillyProject = hillyProjectDao.findById(id).get();
        return hillyProject;
    }

    public HillyProject add(HillyProject hillyProject,String id) {
        Hilly hilly = new Hilly();
        hilly.setName(hillyProject.getProjectName());
        String _id = hillyService.addHilly(hilly);    //??????hilly?????????????????????id
        hillyProject.setCreateDate(new Date());
        hillyProject.setChangeDate(new Date());
        hillyProject.setHillyId(_id);   //???hillyProject?????????hillyId????????????
        hillyProject.setUserId(id);
        HillyProject save = hillyProjectDao.save(hillyProject);
        return save;
    }

    public long update(HillyProject hillyProject) {
        String id = hillyProject.get_id();
        String hillyId = hillyProject.getHillyId();
        /**
         * ???????????????????????????????????????
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
         * ??????????????????????????????????????????
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
         * ???????????????????????????????????????
         */
        Query query = new Query(Criteria.where("_id").is(id));
        DeleteResult deleteResult = mongoTemplate.remove(query, HillyProject.class, "hillyProject");
        long deletedCount = deleteResult.getDeletedCount();
        /**
         * ????????????????????????????????????
         */
        Query query2 = new Query(Criteria.where("_id").is(hillyId));
        mongoTemplate.remove(query2, Hilly.class, "hilly");

        return deletedCount;
    }

    public PageHelper getAllByPage(String id, Integer currentPage, Integer pageSize) {
        Query query = new Query(Criteria.where("userId").is(id));
        mongoUtil.start(currentPage, pageSize, query);
        List<HillyProject> projects = mongoTemplate.find(query, HillyProject.class);
        long count = mongoTemplate.count(query, HillyProject.class);
        PageHelper pageHelper = mongoUtil.pageHelper(count, projects);
        return pageHelper;
    }
}
