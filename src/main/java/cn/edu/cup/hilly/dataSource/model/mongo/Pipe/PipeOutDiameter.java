package cn.edu.cup.hilly.dataSource.model.mongo.Pipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class PipeOutDiameter {

    private String name;
    private String unit;
    private String value;

    public PipeOutDiameter() {
        this.name = "外径";
        this.unit = "m";
        this.value = "";
    }
}
