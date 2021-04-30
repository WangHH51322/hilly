package cn.edu.cup.hilly.dataSource.model.mongo.Pipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class PipeRoughness {

    private String name;
    private String unit;
    private String value;

    public PipeRoughness() {
        this.name = "绝对粗糙度";
        this.unit = "m";
        this.value = "0.0";
    }
}
