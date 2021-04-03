package cn.edu.cup.hilly.dataSource.model.mongo.interInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class WarnInfo {

    private String name;
    private String unit;
    private String value;

    public WarnInfo() {
        this.name = "警告提示";
        this.unit = "";
        this.value = "";
    }
}
