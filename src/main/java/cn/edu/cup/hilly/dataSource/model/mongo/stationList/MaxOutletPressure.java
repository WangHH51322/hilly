package cn.edu.cup.hilly.dataSource.model.mongo.stationList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class MaxOutletPressure {

    private String name;
    private String unit;
    private String value;

    public MaxOutletPressure() {
        this.name = "最大出站压力";
        this.unit = "MPa";
        this.value = "0.0";
    }
}
