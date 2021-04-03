package cn.edu.cup.hilly.dataSource.model.mongo.mediumList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class MediumName {

    private String name;
    private String unit;
    private String value;

    public MediumName() {
        this.name = "流体介质名称";
        this.unit = "";
        this.value = "";
    }
}
