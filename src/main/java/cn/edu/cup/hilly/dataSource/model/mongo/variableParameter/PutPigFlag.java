package cn.edu.cup.hilly.dataSource.model.mongo.variableParameter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class PutPigFlag {

    private String name;
    private String unit;
    private String value;

    public PutPigFlag() {
        this.name = "投放清管器标记";
        this.unit = "";
        this.value = "0";
    }
}
