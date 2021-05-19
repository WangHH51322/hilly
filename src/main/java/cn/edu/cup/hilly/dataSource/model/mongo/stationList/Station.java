package cn.edu.cup.hilly.dataSource.model.mongo.stationList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class Station {
    private StationId stationId;
    private StationName stationName;
    private StationType stationType;
    private StationPumps stationPumps;
    private StationValves stationValves;
    private StationL stationL;
    private StationZ stationZ;

    public Station() {
        this.stationId = new StationId();
        this.stationName = new StationName();
        this.stationType = new StationType();
//        this.stationPumps = new StationPumps();
//        this.stationValves = new StationValves();
        this.stationL = new StationL();
        this.stationZ = new StationZ();
    }
}
