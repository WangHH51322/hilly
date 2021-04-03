package cn.edu.cup.hilly.dataSource.model.mongo.variableParameter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class TimeStep {

    private String name;
    private String unit;
    private String value;

    public TimeStep() {
        this.name = "时间步长";
        this.unit = "s";
        this.value = "";
    }
}
