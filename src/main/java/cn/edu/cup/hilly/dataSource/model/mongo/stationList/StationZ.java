package cn.edu.cup.hilly.dataSource.model.mongo.stationList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class StationZ {

    private String name;
    private String unit;
    private String value;

    public StationZ() {
        this.name = "站点高程";
        this.unit = "m";
        this.value = "0.0";
    }
}
