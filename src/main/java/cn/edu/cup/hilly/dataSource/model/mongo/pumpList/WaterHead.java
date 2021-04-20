package cn.edu.cup.hilly.dataSource.model.mongo.pumpList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class WaterHead {

    private String name;
    private String unit;
    private String value;

    public WaterHead() {
        this.name = "离心泵扬程";
        this.unit = "m";
        this.value = "";
    }
}