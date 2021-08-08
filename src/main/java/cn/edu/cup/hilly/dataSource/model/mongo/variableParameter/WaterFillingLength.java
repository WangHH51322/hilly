package cn.edu.cup.hilly.dataSource.model.mongo.variableParameter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class WaterFillingLength {

    private String name;
    private String unit;
    private String value;

    public WaterFillingLength() {
        this.name = "充水长度";
        this.unit = "km";
        this.value = "0.0";
    }
}
