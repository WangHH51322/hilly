package cn.edu.cup.hilly.dataSource.controller;

import cn.edu.cup.base.CommonProvider;
import cn.edu.cup.hilly.calculate.hilly.large.Project;
import cn.edu.cup.hilly.dataSource.Service.mongo.HillyService;
import cn.edu.cup.hilly.dataSource.model.mongo.DataMap;
import cn.edu.cup.hilly.dataSource.model.mongo.Hilly;
import cn.edu.cup.hilly.dataSource.model.mongo.Pipe.Pipe;
import cn.edu.cup.hilly.dataSource.model.mongo.mediumList.Medium;
import cn.edu.cup.hilly.dataSource.model.mongo.mediumList.MediumList;
import cn.edu.cup.hilly.dataSource.model.mongo.pigList.Pig;
import cn.edu.cup.hilly.dataSource.model.mongo.pigList.PigList;
import cn.edu.cup.hilly.dataSource.model.mongo.siteInfo.SiteInfo;
import cn.edu.cup.hilly.dataSource.model.mongo.variableParameter.VariableParameter;
import cn.edu.cup.hilly.dataSource.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hilly")
public class HillyController {

    @Autowired
    HillyService hillyService;

    /**
     * 查询全部的项目数据信息
     * @return
     */
    @GetMapping("/getAll")
    public RespBean getAllHilly() {
        List<Hilly> hilly =  hillyService.getAllHilly();
        return RespBean.ok("查询成功!",hilly);
    }
    /**
     * 根据项目id查询项目数据信息
     * @param id
     * @return
     */
    @GetMapping("/getById")
    public RespBean getHillyById(@RequestParam("id") String id) {
        Hilly hillyById = hillyService.getHillyById(id);
        return RespBean.ok("查询成功!",hillyById);
    }

    /**
     * 根据项目id查询站点信息
     * @param id
     * @return
     */
    @GetMapping("/siteInfo")
    public RespBean getSiteInfoById(@RequestParam("id") String id) {
        SiteInfo siteInfo = hillyService.getSiteInfoById(id);
        return RespBean.ok("站点信息查询成功!",siteInfo);
    }
    /**
     * 更新站点信息
     * @param id
     * @param siteInfo
     * @return
     */
    @PutMapping("/siteInfo")
    public RespBean updateSiteInfoById(@RequestParam("id") String id ,@RequestBody SiteInfo siteInfo) {
        long l = hillyService.updateSiteInfoById(id, siteInfo);
        if (l == 1) {
            return RespBean.ok("数据已更新");
        }
        return RespBean.error("数据更新失败!");
    }

    /**
     * 根据项目id获取全部流体介质
     * @param id
     * @return
     */
    @GetMapping("/mediumList")
    public RespBean getMediumListById(@RequestParam("id") String id) {
        MediumList mediumList = hillyService.getMediumListById(id);
        return RespBean.ok("流体介质列表查询成功!",mediumList);
    }
    /**
     * 根据项目id更新全部流体介质
     * @param id
     * @param mediumList
     * @return
     */
    @PutMapping("/mediumList")
    public RespBean updateMediumListById(@RequestParam("id") String id ,@RequestBody MediumList mediumList) {
        long l = hillyService.updateMediumListById(id, mediumList);
        if (l == 1) {
            return RespBean.ok("数据更新成功");
        }
        return RespBean.error("数据更新失败!");
    }
    /**
     * 根据项目id添加单个流体介质
     * @param id
     * @return
     */
    @PostMapping("/medium")
    public RespBean addMedium(@RequestParam("id") String id) {
        try {
            Medium medium = hillyService.addMedium(id);
            return RespBean.ok("流体介质添加成功!",medium);
        } catch (Exception e) {
            return RespBean.error("流体介质添加失败!",e.getClass().getName());
        }
    }
    /**
     * 根据项目id更新单个流体介质
     * @param id
     * @param medium
     * @return
     */
    @PutMapping("/medium")
    public RespBean updateMedium(@RequestParam("id") String id,@RequestBody Medium medium) {
        try {
            hillyService.updateMedium(id,medium);
            return RespBean.ok("流体介质更新成功");
        } catch (Exception e) {
            return RespBean.error("流体介质更新失败",e.getClass().getName());
        }
    }

    /**
     * 根据项目id查询变量信息
     * @param id
     * @return
     */
    @GetMapping("/variableParameter")
    public RespBean getVPById(@RequestParam("id") String id) {
        VariableParameter vpById = hillyService.getVPById(id);
        return RespBean.ok("参数查询成功!",vpById);
    }
    /**
     * 根据项目id更新变量信息
     * @param id
     * @param variableParameter
     * @return
     */
    @PutMapping("/variableParameter")
    public RespBean updateVPById(@RequestParam("id") String id ,@RequestBody VariableParameter variableParameter) {
        long l = hillyService.updateVPById(id, variableParameter);
        if (l == 1) {
            return RespBean.ok("数据更新成功");
        }
        return RespBean.error("数据更新失败!");
    }

    /**
     * 根据项目id查询清管器列表信息
     * @param id
     * @return
     */
    @GetMapping("/pigList")
    public RespBean getPigListById(@RequestParam("id") String id) {
        PigList pigList = hillyService.getPigListById(id);
        return RespBean.ok("清管器列表查询成功!",pigList);
    }
    /**
     * 根据项目id更新清管器列表信息
     * @param id
     * @param pigList
     * @return
     */
    @PutMapping("/pigList")
    public RespBean updatePigListById(@RequestParam("id") String id ,@RequestBody PigList pigList) {
        long l = hillyService.updatePigListById(id, pigList);
        if (l == 1) {
            return RespBean.ok("数据更新成功");
        }
        return RespBean.error("数据更新失败!");
    }
    /**
     * 根据项目id添加单个清管器
     * @param id
     * @return
     */
    @PostMapping("/pig")
    public RespBean addPig(@RequestParam("id") String id) {
        try {
            Pig pig = hillyService.addPig(id);
            return RespBean.ok("清管器添加成功!",pig);
        } catch (Exception e) {
            return RespBean.error("清管器添加失败!",e.getClass().getName());
        }
    }
    /**
     * 根据项目id更新单个清管器
     * @param id
     * @param pig
     * @return
     */
    @PutMapping("/pig")
    public RespBean updatePig(@RequestParam("id") String id,@RequestBody Pig pig) {
        try {
            hillyService.updatePig(id,pig);
            return RespBean.ok("清管器更新成功");
        } catch (Exception e) {
            return RespBean.error("清管器更新失败",e.getClass().getName());
        }
    }

    /**
     * 根据项目id查询管段信息
     * @param id
     * @return
     */
    @GetMapping("/pipe")
    public RespBean getPipeById(@RequestParam("id") String id) {
        Pipe pipeById = hillyService.getPipeById(id);
        return RespBean.ok("管段查询成功!",pipeById);
    }
    /**
     * 根据项目id更新管段信息
     * @param id
     * @param pipe
     * @return
     */
    @PutMapping("/pipe")
    public RespBean updatePipeById(@RequestParam("id") String id ,@RequestBody Pipe pipe) {
        long l = hillyService.updatePipeById(id, pipe);
        if (l == 1) {
            return RespBean.ok("数据更新成功");
        }
        return RespBean.error("数据更新失败!");
    }

    /**
     * 调用方法求解
     * @param id
     * @return
     */
    @GetMapping("/run")
    public RespBean run(@RequestParam("id") String id) {
        Project project = new Project();
        Hilly hilly = hillyService.getHillyById(id);
        Map<String, Object> dataMap = DataMap.getDataMap(hilly);

        CommonProvider commonProvider = new CommonProvider();
        commonProvider.setDataMap(dataMap);
        commonProvider.startDataRequirementProcess(project);

        project.run();
        return RespBean.ok("计算成功");
    }

}
