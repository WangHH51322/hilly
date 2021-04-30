package cn.edu.cup.hilly.dataSource.model.mongo.stationList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class StationType {

    private String name;
    private String unit;
    private String value;

    public StationType() {
        this.name = "站点类型";
        this.unit = "m";
        this.value = "0";
    }
}
