package cn.edu.cup.hilly.dataSource.model.mongo.pigList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class PigArea {

    private String name;
    private String unit;
    private String value;

    public PigArea() {
        this.name = "清管器截面积";
        this.unit = "m^2";
        this.value = "";
    }
}
