package cn.edu.cup.hilly.dataSource.model.mongo.mediumList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Data
@AllArgsConstructor
public class MediumList {
    private String name;
    private String unit;
    private List<Medium> value;
    public MediumList() {
        this.name = "流体介质列表";
        this.unit = "";
        this.value = new ArrayList<>();
        value.add(new Medium());
    }
}
