package cn.edu.cup.hilly.dataSource.model.mongo.valveList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class ValveType {

    private String name;
    private String unit;
    private String value;

    public ValveType() {
        this.name = "阀门类型";
        this.unit = "";
        this.value = "";
    }
}
