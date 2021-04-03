package cn.edu.cup.hilly.dataSource.model.mongo.variableParameter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class Sections {

    private String name;
    private String unit;
    private String value;

    public Sections() {
        this.name = "大落差段数";
        this.unit = "";
        this.value = "";
    }
}
