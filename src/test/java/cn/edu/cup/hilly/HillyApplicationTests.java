package cn.edu.cup.hilly;

import cn.edu.cup.base.CommonProvider;
import cn.edu.cup.hilly.calculate.hilly.large.Project;
import cn.edu.cup.hilly.dataSource.model.mongo.mediumList.MediumList;
import cn.edu.cup.hilly.dataSource.model.mongo.result.TempTest;
import cn.edu.cup.hilly.dataSource.service.mongo.HillyLiquidService;
import cn.edu.cup.hilly.dataSource.service.mongo.HillyProjectService;
import cn.edu.cup.hilly.dataSource.service.mongo.HillyService;
import cn.edu.cup.hilly.dataSource.model.mongo.DataMap;
import cn.edu.cup.hilly.dataSource.model.mongo.Hilly;
import cn.edu.cup.hilly.dataSource.model.mongo.project.HillyProject;
import cn.edu.cup.hilly.dataSource.service.mongo.TempTestSer;
//import cn.edu.cup.hilly.dataSource.service.mongo.TempTestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class HillyApplicationTests {

    @Autowired
    HillyService hillyService;
    @Autowired
    HillyProjectService hillyProjectService;
    @Autowired
    TempTestSer tempTestService;
    @Autowired
    HillyLiquidService hillyLiquidService;

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
//        Project project = new Project();
        Hilly hilly = hillyService.getHillyById("60910cfadbe2e64305cbdf08");
        MediumList mediumListById = hillyLiquidService.getMediumListById("60910cfadbe2e64305cbdf08");
        System.out.println("mediumListById:" + mediumListById);
//        Map<String, Object> dataMap = DataMap.getDataMap(hilly);

//
//        CommonProvider commonProvider = new CommonProvider();
//        commonProvider.setDataMap(dataMap);
//        commonProvider.startDataRequirementProcess(project);
//
//        project.run();
    }

    @Test
    void test02() {
        TempTest tempTest = new TempTest();
        tempTest.setLocation("第二段U型管");
        double[][] arr = new double[3][3];
        tempTest.setHis(arr);
        tempTestService.add(tempTest);
    }

    @Test
    void test03() {
        String s = "[1,2,3,4]";
        s = s.replace("[","");
        s = s.replace("]","");
        String[] ss = s.split(",");
        int[] ii = new int[ss.length];
        for (int i = 0; i < ii.length; i++) {
            ii[i] = Integer.parseInt(ss[i]);
            System.out.println(ii[i]);
        }
        System.out.println(ss.length);
    }
 }
