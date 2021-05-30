package cn.edu.cup.hilly.dataSource.model.mongo.stationList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class Station implements Comparable {
    private StationId stationId;
    private StationName stationName;
    private OffLoadFlow OffLoadFlow;
    private StationType stationType;
    private StationPumps stationPumps;
    private StationValves stationValves;
    private StationL stationL;
    private StationZ stationZ;

    public Station() {
        this.stationId = new StationId();
        this.stationName = new StationName();
        this.OffLoadFlow = new OffLoadFlow();
        this.stationType = new StationType();
//        this.stationPumps = new StationPumps();
//        this.stationValves = new StationValves();
        this.stationL = new StationL();
        this.stationZ = new StationZ();
    }

    @Override
    public int compareTo(Object o) {
        Station s = (Station) o;
        return this.stationL.getValue().compareTo(s.stationL.getValue());
    }
}
