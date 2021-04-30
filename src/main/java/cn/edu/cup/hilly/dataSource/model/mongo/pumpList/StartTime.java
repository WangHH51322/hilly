package cn.edu.cup.hilly.dataSource.model.mongo.pumpList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class StartTime {

    private String name;
    private String unit;
    private String value;

    public StartTime() {
        this.name = "启泵时长";
        this.unit = "s";
        this.value = "0.0";
    }
}
