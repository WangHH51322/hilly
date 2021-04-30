package cn.edu.cup.hilly.dataSource.model.mongo.mediumList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class MediumDensity {

    private String name;
    private String unit;
    private String value;

    public MediumDensity() {
        this.name = "流体介质密度";
        this.unit = "kg/m³";
        this.value = "0.0";
    }
}
