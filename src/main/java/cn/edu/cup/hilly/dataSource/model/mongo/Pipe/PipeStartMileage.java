package cn.edu.cup.hilly.dataSource.model.mongo.Pipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class PipeStartMileage {

    private String name;
    private String unit;
    private String value;

    public PipeStartMileage() {
        this.name = "起始计算点位置_里程";
        this.unit = "m";
        this.value = "";
    }
}
