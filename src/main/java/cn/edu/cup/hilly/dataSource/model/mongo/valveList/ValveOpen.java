package cn.edu.cup.hilly.dataSource.model.mongo.valveList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class ValveOpen {

    private String name;
    private String unit;
    private String value;

    public ValveOpen() {
        this.name = "阀门开度";
        this.unit = "m";
        this.value = "";
    }
}
