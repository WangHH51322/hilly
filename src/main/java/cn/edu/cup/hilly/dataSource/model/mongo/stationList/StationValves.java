package cn.edu.cup.hilly.dataSource.model.mongo.stationList;

import cn.edu.cup.hilly.dataSource.model.mongo.valveList.ValveList;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "stationValves")
@Data
@AllArgsConstructor
public class StationValves extends ValveList {
    @Id
    private String _id;

    @Indexed
    private String hillyId;

    @Indexed
    private String stationId;
    public StationValves() {
        super();
        this.setName("站内阀门列表");
    }
}
