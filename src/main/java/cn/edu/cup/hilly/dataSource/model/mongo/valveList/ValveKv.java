package cn.edu.cup.hilly.dataSource.model.mongo.valveList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class ValveKv {

    private String name;
    private String unit;
    private String value;

    public ValveKv() {
        this.name = "阀门流量系数";
        this.unit = "";
        this.value = "[0,1,2,3,4,5,6,7,8,9,10]";
    }
}
