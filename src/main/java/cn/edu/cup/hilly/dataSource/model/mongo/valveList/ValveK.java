package cn.edu.cup.hilly.dataSource.model.mongo.valveList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class ValveK {

    private String name;
    private String unit;
    private String value;

    public ValveK() {
        this.name = "阀门开度";
        this.unit = "";
        this.value = "0.0";
    }
}
