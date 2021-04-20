package cn.edu.cup.hilly.dataSource.model.mongo.pigList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Data
@AllArgsConstructor
public class PigList {
    private String name;
    private String unit;
    private List<Pig> value;

    public PigList() {
        this.name = "清管器列表";
        this.unit = "";
        this.value = new ArrayList<>();
//        value.add(new Pig());
    }
}
