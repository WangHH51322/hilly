package cn.edu.cup.hilly.dataSource.model.mongo.siteInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class SiteKeyName {

    private String name;
    private String unit;
    private String value;

    public SiteKeyName() {
        this.name = "关键点名称";
        this.unit = "";
        this.value = "demo字符串";
    }

}
