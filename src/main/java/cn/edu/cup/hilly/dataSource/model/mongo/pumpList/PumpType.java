package cn.edu.cup.hilly.dataSource.model.mongo.pumpList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class PumpType {

    private String name;
    private String unit;
    private String value;

    public PumpType() {
        this.name = "泵类型";
        this.unit = "";
        this.value = "";
    }
}
