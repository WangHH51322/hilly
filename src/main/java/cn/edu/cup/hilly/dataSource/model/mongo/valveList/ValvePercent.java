package cn.edu.cup.hilly.dataSource.model.mongo.valveList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class ValvePercent {

    private String name;
    private String unit;
    private String value;

    public ValvePercent() {
        this.name = "阀门流量系数百分比";
        this.unit = "";
        this.value = "[0,10,20,30,40,50,60,70,80,90,100]";
    }
}
