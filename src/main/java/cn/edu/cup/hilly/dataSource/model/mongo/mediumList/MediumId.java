package cn.edu.cup.hilly.dataSource.model.mongo.mediumList;

import cn.edu.cup.hilly.dataSource.utils.IdWorker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class MediumId {

    private String name;
    private String unit;
    private String value;

    public MediumId() {
        IdWorker idWorker = new IdWorker();
        this.name = "流体介质编号";
        this.unit = "";
        this.value = String.valueOf(idWorker.nextId());
    }
}
