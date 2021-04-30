package cn.edu.cup.hilly.dataSource.model.mongo.Pipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class PipeLength {

    private String name;
    private String unit;
    private String value;

    public PipeLength() {
        this.name = "管长";
        this.unit = "m";
        this.value = "0.0";
    }
}
