package cn.edu.cup.hilly.dataSource.model.mongo.valveList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class ValveName {

    private String name;
    private String unit;
    private String value;

    public ValveName() {
        this.name = "阀门名称";
        this.unit = "";
        this.value = "";
    }
}
