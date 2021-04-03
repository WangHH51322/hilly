package cn.edu.cup.hilly.dataSource.model.mongo.valveList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Data
@AllArgsConstructor
public class ValveList {
    private String name;
    private String unit;
    private List<Valve> value;

    public ValveList() {
        this.name = "阀门列表";
        this.unit = "";
        this.value = new ArrayList<>();
        value.add(new Valve());
    }
}
