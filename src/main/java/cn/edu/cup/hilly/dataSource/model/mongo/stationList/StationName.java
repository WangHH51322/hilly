package cn.edu.cup.hilly.dataSource.model.mongo.stationList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class StationName {

    private String name;
    private String unit;
    private String value;

    public StationName() {
        this.name = "站点名称";
        this.unit = "m";
        this.value = "";
    }
}
