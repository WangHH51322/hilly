package cn.edu.cup.hilly.dataSource.model.mongo.interInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class PumpStartTime {

    private String name;
    private String unit;
    private String value;

    public PumpStartTime() {
        this.name = "启泵时间";
        this.unit = "s";
        this.value = "0.0";
    }
}
