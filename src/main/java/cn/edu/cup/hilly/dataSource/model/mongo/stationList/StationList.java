package cn.edu.cup.hilly.dataSource.model.mongo.stationList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Data
@AllArgsConstructor
public class StationList {

    private String name;
    private String unit;
    private List<Station> value;

    public StationList() {
        this.name = "站点列表";
        this.unit = "";
        this.value = new ArrayList<>();
        value.add(new Station());
    }
}
