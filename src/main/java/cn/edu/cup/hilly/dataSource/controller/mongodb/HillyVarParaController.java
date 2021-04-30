package cn.edu.cup.hilly.dataSource.controller.mongodb;

import cn.edu.cup.hilly.dataSource.model.mongo.variableParameter.VariableParameter;
import cn.edu.cup.hilly.dataSource.service.mongo.HillyVarParaService;
import cn.edu.cup.hilly.dataSource.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 根据项目id和新的VariableParameter,更新旧VariableParameter
     * @param id
     * @param variableParameter
     * @return
     */
    @PutMapping("/update")
    public RespBean update(@RequestParam("id") String id, @RequestBody VariableParameter variableParameter) {
        try {
            long l = varPara.update(id, variableParameter);
            if (l == 1) {
                return RespBean.ok("更新成功");
            }
            return RespBean.error("未更新数据");
        } catch (Exception e) {
            return RespBean.error("更新出错",e.getClass().getName());
        }
    }

    /**
     * 根据项目id,清空VariableParameter,即恢复默认值
     * @param id
     * @return
     */
    @DeleteMapping("/clear")
    public RespBean clear(@RequestParam("id") String id) {
        try {
            long l = varPara.update(id, new VariableParameter());
            if (l == 1) {
                return RespBean.ok("清空成功");
            }
            return RespBean.error("清空失败");
        } catch (Exception e) {
            return RespBean.error("清空出错",e.getClass().getName());
        }

    }
}
