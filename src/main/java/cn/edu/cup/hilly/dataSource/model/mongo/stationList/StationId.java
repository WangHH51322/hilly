package cn.edu.cup.hilly.dataSource.model.mongo.stationList;

import cn.edu.cup.hilly.dataSource.utils.IdWorker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author wong
 * @date 2021年04月30日 16:45
 */
@ToString
@Data
@AllArgsConstructor
public class StationId {
    private String name;
    private String unit;
    private String value;

    public StationId() {
        IdWorker idWorker = new IdWorker();
        this.name = "泵站点编号";
        this.unit = "";
        this.value = String.valueOf(idWorker.nextId());
    }

}
