package cn.edu.cup.hilly.dataSource.model.mongo.pumpList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class PumpPower {

    private String name;
    private String unit;
    private String value;

    public PumpPower() {
        this.name = "离心泵功率";
        this.unit = "KW";
        this.value = "";
    }
}
