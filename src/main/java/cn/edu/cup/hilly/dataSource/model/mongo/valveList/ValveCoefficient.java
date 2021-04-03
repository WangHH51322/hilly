package cn.edu.cup.hilly.dataSource.model.mongo.valveList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class ValveCoefficient {

    private String name;
    private String unit;
    private String value;

    public ValveCoefficient() {
        this.name = "阀门系数";
        this.unit = "";
        this.value = "";
    }
}
