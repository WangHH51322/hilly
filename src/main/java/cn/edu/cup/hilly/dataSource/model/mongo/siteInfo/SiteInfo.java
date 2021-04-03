package cn.edu.cup.hilly.dataSource.model.mongo.siteInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class SiteInfo {
    private SiteKeyAltitude siteKeyAltitude;
    private SiteKeyMileage siteKeyMileage;
    private SiteKeyName siteKeyName;

    public SiteInfo() {
        this.siteKeyAltitude = new SiteKeyAltitude();
        this.siteKeyMileage = new SiteKeyMileage();
        this.siteKeyName = new SiteKeyName();
    }
}
