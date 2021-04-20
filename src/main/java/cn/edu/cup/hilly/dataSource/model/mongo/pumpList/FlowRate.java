package cn.edu.cup.hilly.dataSource.model.mongo.pumpList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class FlowRate {

    private String name;
    private String unit;
    private String value;

    public FlowRate() {
        this.name = "离心泵流量";
        this.unit = "m3/h";
        this.value = "";
    }
}
