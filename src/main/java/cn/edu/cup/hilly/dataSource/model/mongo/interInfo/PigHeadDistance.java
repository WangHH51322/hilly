package cn.edu.cup.hilly.dataSource.model.mongo.interInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class PigHeadDistance {

    private String name;
    private String unit;
    private String value;

    public PigHeadDistance() {
        this.name = "投放前清管器与水头距离";
        this.unit = "km";
        this.value = "0.0";
    }
}
