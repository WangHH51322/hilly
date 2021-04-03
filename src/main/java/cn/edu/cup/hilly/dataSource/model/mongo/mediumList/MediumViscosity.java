package cn.edu.cup.hilly.dataSource.model.mongo.mediumList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class MediumViscosity {

    private String name;
    private String unit;
    private String value;

    public MediumViscosity() {
        this.name = "流体介质粘度";
        this.unit = "mPa.s";
        this.value = "";
    }
}
