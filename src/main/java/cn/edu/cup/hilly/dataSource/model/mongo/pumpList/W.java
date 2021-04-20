package cn.edu.cup.hilly.dataSource.model.mongo.pumpList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class W {

    private String name;
    private String unit;
    private String value;

    public W() {
        this.name = "离心泵功率";
        this.unit = "KW";
        this.value = "";
    }
}
