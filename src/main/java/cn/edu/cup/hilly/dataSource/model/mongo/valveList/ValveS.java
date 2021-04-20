package cn.edu.cup.hilly.dataSource.model.mongo.valveList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class ValveS {

    private String name;
    private String unit;
    private String value;

    public ValveS() {
        this.name = "阀门位置";
        this.unit = "m";
        this.value = "";
    }
}
