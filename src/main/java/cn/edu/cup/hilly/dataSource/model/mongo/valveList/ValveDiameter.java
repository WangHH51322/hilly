package cn.edu.cup.hilly.dataSource.model.mongo.valveList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class ValveDiameter {

    private String name;
    private String unit;
    private String value;

    public ValveDiameter() {
        this.name = "阀门直径";
        this.unit = "m";
        this.value = "";
    }
}
