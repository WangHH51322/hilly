package cn.edu.cup.hilly.dataSource.model.mongo.valveList;

import cn.edu.cup.hilly.dataSource.utils.IdWorker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class ValveId {

    private String name;
    private String unit;
    private String value;

    public ValveId() {
        IdWorker idWorker = new IdWorker();
        this.name = "阀门编号";
        this.unit = "";
        this.value = String.valueOf(idWorker.nextId());
    }
}
