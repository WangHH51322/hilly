package cn.edu.cup.hilly;

import cn.edu.cup.base.CommonProvider;
import cn.edu.cup.hilly.calculate.hilly.large.Project;
import cn.edu.cup.hilly.dataSource.Service.mongo.HillyProjectService;
import cn.edu.cup.hilly.dataSource.Service.mongo.HillyService;
import cn.edu.cup.hilly.dataSource.model.mongo.DataMap;
import cn.edu.cup.hilly.dataSource.model.mongo.Hilly;
import cn.edu.cup.hilly.dataSource.model.mongo.project.HillyProject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootTest
class HillyApplicationTests {

    @Autowired
    HillyService hillyService;
    @Autowired
    HillyProjectService hillyProjectService;

    @Test
    void contextLoads() {
        HillyProject hillyProject = new HillyProject();
        hillyProject.setProjectName("hilly002");
        hillyProject.setAuthor("杨小起");
//        hillyProject.setCreateDate(new Date());
//        hillyProject.setChangeDate(new Date());
//        hillyProject.setHillyId("605dee2a8ad5c03cc7cc663a");
        hillyProject.setRemarks("这是一个小小落差项目");

        HillyProject save = hillyProjectService.add(hillyProject);
        System.out.println(save);
    }

    @Test
    void test01() {
        Project project = new Project();
        Hilly hilly = hillyService.getHillyById("6066b58ec26f66377d192515");
        Map<String, Object> dataMap = DataMap.getDataMap(hilly);

        CommonProvider commonProvider = new CommonProvider();
        commonProvider.setDataMap(dataMap);
        commonProvider.startDataRequirementProcess(project);

        project.run();
        //获取数据
        System.out.println(project.getPg_his());
    }
}
