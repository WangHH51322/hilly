package cn.edu.cup.hilly.dataSource.model.mongo.pumpList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class PumpState {

    private String name;
    private String unit;
    private String value;

    public PumpState() {
        this.name = "泵状态";
        this.unit = "";
        this.value = "0";
    }
}
