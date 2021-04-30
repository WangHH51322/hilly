package cn.edu.cup.hilly.dataSource.model.mongo.stationList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class StationL {

    private String name;
    private String unit;
    private String value;

    public StationL() {
        this.name = "站点里程";
        this.unit = "m";
        this.value = "0.0";
    }
}
