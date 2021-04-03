package cn.edu.cup.hilly.dataSource.model.mongo.variableParameter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class SimulationSteps {

    private String name;
    private String unit;
    private String value;

    public SimulationSteps() {
        this.name = "模拟步数";
        this.unit = "";
        this.value = "";
    }
}
