package cn.edu.cup.hilly.dataSource.model.mongo.stationList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class OffLoadFlow {

    private String name;
    private String unit;
    private String value;

    public OffLoadFlow() {
        this.name = "分输流量";
        this.unit = "m";
        this.value = "0.0";
    }
}
