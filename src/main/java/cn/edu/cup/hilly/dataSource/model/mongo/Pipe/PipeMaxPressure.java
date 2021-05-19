package cn.edu.cup.hilly.dataSource.model.mongo.Pipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author CodeChap
 * @date 2021-05-19 10:53
 */

@ToString
@Data
@AllArgsConstructor
public class PipeMaxPressure {
    private String name;
    private String unit;
    private String value;

    public PipeMaxPressure() {
        this.name = "管道最大承压";
        this.unit = "MPa";
        this.value = "0.0";
    }
}
