package cn.edu.cup.hilly.dataSource.model.mongo.pumpList;

import cn.edu.cup.hilly.dataSource.utils.IdWorker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class PumpId {

    private String name;
    private String unit;
    private String value;

    public PumpId() {
        IdWorker idWorker = new IdWorker();
        this.name = "泵编号";
        this.unit = "";
        this.value = String.valueOf(idWorker.nextId());
    }
}
