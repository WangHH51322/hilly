package cn.edu.cup.hilly.dataSource.model.mongo.Pipe;

import cn.edu.cup.hilly.dataSource.utils.IdWorker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class PipeId {

    private String name;
    private String unit;
    private String value;

    public PipeId() {
        IdWorker idWorker = new IdWorker();
        this.name = "管段编号";
        this.unit = "";
        this.value = String.valueOf(idWorker.nextId());
    }
}
