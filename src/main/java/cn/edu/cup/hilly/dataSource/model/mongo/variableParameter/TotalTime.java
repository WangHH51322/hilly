package cn.edu.cup.hilly.dataSource.model.mongo.variableParameter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class TotalTime {

    private String name;
    private String unit;
    private String value;

    public TotalTime() {
        this.name = "总模拟时长";
        this.unit = "h";
        this.value = "0.0";
    }
}
