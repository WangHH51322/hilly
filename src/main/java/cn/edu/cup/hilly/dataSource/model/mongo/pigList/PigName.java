package cn.edu.cup.hilly.dataSource.model.mongo.pigList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class PigName {

    private String name;
    private String unit;
    private String value;

    public PigName() {
        this.name = "清管器名称";
        this.unit = "";
        this.value = "";
    }
}
