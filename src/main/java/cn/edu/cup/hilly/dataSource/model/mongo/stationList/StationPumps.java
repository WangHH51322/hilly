package cn.edu.cup.hilly.dataSource.model.mongo.stationList;

import cn.edu.cup.hilly.dataSource.model.mongo.pumpList.PumpList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.security.SecureRandom;

@Document(value = "stationPumps")
@Data
@AllArgsConstructor
public class StationPumps extends PumpList {
    @Id
    private String _id;

    @Indexed
    private String hillyId;

    @Indexed
    private String stationId;

    public StationPumps() {
        super();
        this.setName("站内离心泵列表");
    }
}
