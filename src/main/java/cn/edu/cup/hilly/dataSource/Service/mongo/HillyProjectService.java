package cn.edu.cup.hilly.dataSource.Service.mongo;

import cn.edu.cup.hilly.dataSource.mapper.mongo.HillyProjectDao;
import cn.edu.cup.hilly.dataSource.model.mongo.Hilly;
import cn.edu.cup.hilly.dataSource.model.mongo.interInfo.InterInfo;
import cn.edu.cup.hilly.dataSource.model.mongo.mediumList.Medium;
import cn.edu.cup.hilly.dataSource.model.mongo.mediumList.MediumList;
import cn.edu.cup.hilly.dataSource.model.mongo.pigList.Pig;
import cn.edu.cup.hilly.dataSource.model.mongo.pigList.PigList;
import cn.edu.cup.hilly.dataSource.model.mongo.project.HillyProject;
import cn.edu.cup.hilly.dataSource.model.mongo.pumpList.PumpList;
import cn.edu.cup.hilly.dataSource.model.mongo.siteInfo.SiteInfo;
import cn.edu.cup.hilly.dataSource.model.mongo.valveList.Valve;
import cn.edu.cup.hilly.dataSource.model.mongo.valveList.ValveList;
import cn.edu.cup.hilly.dataSource.model.mongo.variableParameter.VariableParameter;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<HillyProject> getAll() {
        return hillyProjectDao.findAll();
    }

    public HillyProject save(HillyProject hillyProject) {
        HillyProject save = hillyProjectDao.save(hillyProject);
        return save;
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
}
