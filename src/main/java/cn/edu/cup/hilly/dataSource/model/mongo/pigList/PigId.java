package cn.edu.cup.hilly.dataSource.model.mongo.pigList;

import cn.edu.cup.hilly.dataSource.utils.IdWorker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class PigId {

    private String name;
    private String unit;
    private String value;

    public PigId() {
        IdWorker idWorker = new IdWorker();
        this.name = "清管器编号";
        this.unit = "";
        this.value = String.valueOf(idWorker.nextId());
    }
}
