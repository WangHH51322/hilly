package cn.edu.cup.hilly.dataSource.model.mongo.mediumList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class MediumTemperature {

    private String name;
    private String unit;
    private String value;

    public MediumTemperature() {
        this.name = "流体介质温度";
        this.unit = "K";
        this.value = "";
    }
}
