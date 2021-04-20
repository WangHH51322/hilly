package cn.edu.cup.hilly.dataSource.model.mongo.variableParameter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class DesignFlow {

    private String name;
    private String unit;
    private String value;

    public DesignFlow() {
        this.name = "设计投产流量";
        this.unit = "m3/h";
        this.value = "";
    }
}
