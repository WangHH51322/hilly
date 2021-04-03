package cn.edu.cup.hilly.dataSource.model.mongo.pumpList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Data
@AllArgsConstructor
public class PumpList {

    private String name;
    private String unit;
    private List<Pump> value;

    public PumpList() {
        this.name = "离心泵列表";
        this.unit = "";
        this.value = new ArrayList<>();
        value.add(new Pump());
    }
}
