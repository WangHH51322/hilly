package cn.edu.cup.hilly.dataSource.model.mongo.Pipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class PipeThickness {

    private String name;
    private String unit;
    private String value;

    public PipeThickness() {
        this.name = "壁厚";
        this.unit = "m";
        this.value = "";
    }
}
