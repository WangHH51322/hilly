package cn.edu.cup.hilly.dataSource.model.mongo.Pipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class PipeStartAltitude {

    private String name;
    private String unit;
    private String value;

    public PipeStartAltitude() {
        this.name = "起始计算点位置_高程";
        this.unit = "m";
        this.value = "";
    }
}
