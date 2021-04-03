package cn.edu.cup.hilly.dataSource.model.mongo.pumpList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class PumpName {

    private String name;
    private String unit;
    private String value;

    public PumpName() {
        this.name = "离心泵名称";
        this.unit = "";
        this.value = "";
    }
}
