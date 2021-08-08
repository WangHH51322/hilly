package cn.edu.cup.hilly.dataSource.model.mongo.variableParameter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class PressureMode {

    private String name;
    private String unit;
    private String value;

    public PressureMode() {
        this.name = "压力模式";
        this.unit = "";
        this.value = "0";
    }
}
