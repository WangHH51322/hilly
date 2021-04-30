package cn.edu.cup.hilly.dataSource.model.mongo.valveList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class ValveT {

    private String name;
    private String unit;
    private String value;

    public ValveT() {
        this.name = "启动时间";
        this.unit = "s";
        this.value = "0.0";
    }
}
