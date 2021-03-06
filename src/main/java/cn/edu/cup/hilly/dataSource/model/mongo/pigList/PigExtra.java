package cn.edu.cup.hilly.dataSource.model.mongo.pigList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class PigExtra {

    private String name;
    private String unit;
    private String value;

    public PigExtra() {
        this.name = "θΏηι";
        this.unit = "";
        this.value = "";
    }
}
