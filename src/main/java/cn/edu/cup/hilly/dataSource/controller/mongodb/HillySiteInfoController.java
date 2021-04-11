package cn.edu.cup.hilly.dataSource.controller.mongodb;

import cn.edu.cup.hilly.dataSource.model.mongo.siteInfo.SiteInfo;
import cn.edu.cup.hilly.dataSource.service.mongo.HillySiteInfoService;
import cn.edu.cup.hilly.dataSource.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/siteInfo")
public class HillySiteInfoController {
    @Autowired
    HillySiteInfoService service;

    /**
     * 根据项目数据id查询站点信息
     * @param id
     * @return SiteInfo
     */
    @GetMapping("/getById")
    public RespBean getById(@RequestParam("id") String id) {
        try {
            SiteInfo siteInfo = service.getSiteInfoById(id);
            return RespBean.ok("站点信息查询成功!",siteInfo);
        } catch (Exception e) {
            return RespBean.error("站点信息查询出错!",e.getClass().getName());
        }
    }

    /**
     * 根据项目数据id添加站点信息
     * @param hid
     * @param
     * @return
     */
    @PostMapping("/insert")
    public RespBean add(@RequestParam("id") String hid, @RequestBody SiteInfo siteInfo) {
        try {
            long add = service.add(hid, siteInfo);
            if (add == 1) {
                return RespBean.ok("站点信息添加成功!");
            }
            return RespBean.error("站点信息添加失败!");
        }catch (Exception e) {
            return RespBean.error("站点信息添加出错!",e.getClass().getName());
        }
    }

    /**
     * 根据项目数据id更新站点信息
     * @param hid
     * @param
     * @return
     */
    @PutMapping("/update")
    public RespBean update(@RequestParam("id") String hid, @RequestBody SiteInfo siteInfo) {
        try {
            long update = service.add(hid, siteInfo);
            if (update == 1) {
                return RespBean.ok("站点信息更新成功!");
            }
            return RespBean.error("站点信息更新失败!");
        }catch (Exception e) {
            return RespBean.error("站点信息更新出错!",e.getClass().getName());
        }
    }

    /**
     * 根据项目数据id删除站点信息
     * @param hid
     * @param
     * @return
     */
    @DeleteMapping("/delete")
    public RespBean delete(@RequestParam("id") String hid) {
        try {
            long delete = service.delete(hid);
            if (delete == 1) {
                return RespBean.ok("站点信息删除成功!");
            }
            return RespBean.error("站点信息删除失败!");
        }catch (Exception e) {
            return RespBean.error("站点信息删除出错!",e.getClass().getName());
        }
    }
}
