package cn.edu.cup.hilly.dataSource.model.mongo.pumpList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class Rev {

    private String name;
    private String unit;
    private String value;

    public Rev() {
        this.name = "离心泵转速";
        this.unit = "r/min";
        this.value = "0.0";
    }
}
