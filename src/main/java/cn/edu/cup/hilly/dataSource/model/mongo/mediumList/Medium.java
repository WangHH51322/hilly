package cn.edu.cup.hilly.dataSource.model.mongo.mediumList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class Medium {
    private MediumId mediumId;
    private MediumDensity mediumDensity;
    private MediumName mediumName;
    private MediumTemperature mediumTemperature;
    private MediumViscosity mediumViscosity;

    public Medium() {
        this.mediumId = new MediumId();
        this.mediumDensity = new MediumDensity();
        this.mediumName = new MediumName();
        this.mediumTemperature = new MediumTemperature();
        this.mediumViscosity = new MediumViscosity();
    }
}
