package cn.edu.cup.hilly.dataSource.controller.mongodb;

import cn.edu.cup.hilly.dataSource.model.mongo.variableParameter.VariableParameter;
import cn.edu.cup.hilly.dataSource.service.mongo.HillyVarParaService;
import cn.edu.cup.hilly.dataSource.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/varPara")
public class HillyVarParaController {
    @Autowired
    HillyVarParaService varPara;

    /**
     * 根据项目id查询变量信息
     * @param id
     * @return
     */
    @GetMapping("/getById")
    public RespBean getVPById(@RequestParam("id") String id) {
        try {
            VariableParameter vpById = varPara.getById(id);
            return RespBean.ok("参数查询成功!",vpById);
        } catch (Exception e) {
            return RespBean.error("查询出错",e.getClass().getName());
        }

    }
}
