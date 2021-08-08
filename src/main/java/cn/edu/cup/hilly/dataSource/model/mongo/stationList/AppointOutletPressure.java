package cn.edu.cup.hilly.dataSource.model.mongo.stationList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class AppointOutletPressure {

    private String name;
    private String unit;
    private String value;

    public AppointOutletPressure() {
        this.name = "指定压力";
        this.unit = "MPa";
        this.value = "0.0";
    }
}
