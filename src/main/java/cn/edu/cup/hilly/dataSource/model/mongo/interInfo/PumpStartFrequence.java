package cn.edu.cup.hilly.dataSource.model.mongo.interInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class PumpStartFrequence {

    private String name;
    private String unit;
    private String value;

    public PumpStartFrequence() {
        this.name = "启泵频率";
        this.unit = "";
        this.value = "0.0";
    }
}
