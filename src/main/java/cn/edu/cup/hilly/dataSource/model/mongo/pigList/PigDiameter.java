package cn.edu.cup.hilly.dataSource.model.mongo.pigList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class PigDiameter {

    private String name;
    private String unit;
    private String value;

    public PigDiameter() {
        this.name = "清管器直径";
        this.unit = "m";
        this.value = "";
    }
}
