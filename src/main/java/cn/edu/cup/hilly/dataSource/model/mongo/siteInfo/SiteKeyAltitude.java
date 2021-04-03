package cn.edu.cup.hilly.dataSource.model.mongo.siteInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class SiteKeyAltitude {

    private String name;
    private String unit;
    private String value;

    public SiteKeyAltitude() {
        this.name = "关键点高程";
        this.unit = "m";
        this.value = "";
    }
}
