package cn.edu.cup.hilly.dataSource.model.mongo.variableParameter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class HighOpen {

    private String name;
    private String unit;
    private String value;

    public HighOpen() {
        this.name = "高点排气开关";
        this.unit = "";
        this.value = "0";
    }
}
