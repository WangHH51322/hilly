package cn.edu.cup.hilly.dataSource.model.mongo.siteInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class SiteKeyMileage {

    private String name;
    private String unit;
    private String value;

    public SiteKeyMileage() {
        this.name = "关键点里程";
        this.unit = "km";
        this.value = "0.0";
    }

}
