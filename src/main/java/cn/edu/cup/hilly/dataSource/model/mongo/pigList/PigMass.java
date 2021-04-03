package cn.edu.cup.hilly.dataSource.model.mongo.pigList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class PigMass {

    private String name;
    private String unit;
    private String value;

    public PigMass() {
        this.name = "清管器质量";
        this.unit = "kg";
        this.value = "";
    }
}
