package cn.edu.cup.hilly.dataSource.model.mongo.Pipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class PipeAltitude {

    private String name;
    private String unit;
    private String value;

    public PipeAltitude() {
        this.name = "高程";
        this.unit = "m";
        this.value = "0.0";
    }
}
