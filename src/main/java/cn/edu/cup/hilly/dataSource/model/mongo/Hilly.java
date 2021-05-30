package cn.edu.cup.hilly.dataSource.model.mongo;

import cn.edu.cup.hilly.dataSource.model.mongo.Pipe.Pipe;
import cn.edu.cup.hilly.dataSource.model.mongo.Pipe.PipeList;
import cn.edu.cup.hilly.dataSource.model.mongo.interInfo.InterInfo;
import cn.edu.cup.hilly.dataSource.model.mongo.mediumList.Medium;
import cn.edu.cup.hilly.dataSource.model.mongo.mediumList.MediumList;
import cn.edu.cup.hilly.dataSource.model.mongo.pigList.Pig;
import cn.edu.cup.hilly.dataSource.model.mongo.pigList.PigList;
import cn.edu.cup.hilly.dataSource.model.mongo.pumpList.Pump;
import cn.edu.cup.hilly.dataSource.model.mongo.pumpList.PumpList;
import cn.edu.cup.hilly.dataSource.model.mongo.siteInfo.SiteInfo;
import cn.edu.cup.hilly.dataSource.model.mongo.stationList.Station;
import cn.edu.cup.hilly.dataSource.model.mongo.stationList.StationList;
import cn.edu.cup.hilly.dataSource.model.mongo.valveList.Valve;
import cn.edu.cup.hilly.dataSource.model.mongo.valveList.ValveList;
import cn.edu.cup.hilly.dataSource.model.mongo.variableParameter.VariableParameter;
import cn.edu.cup.hilly.dataSource.utils.IdWorker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@ToString
@Document
public class Hilly {

    @Id
    private String _id;

    private String name;
    private InterInfo interInfo;
    private Medium medium;
    private MediumList mediumList;
    private Pig pig;
    private PigList pigList;
    private Pipe pipe;
    private PipeList pipeList;
    private Pump pump;
    private PumpList pumpList;
    private SiteInfo siteInfo;
    private Valve valve;
    private ValveList valveList;
    private VariableParameter variableParameter;
    private Station station;
    private StationList stationList;

    public Hilly() {
        this.interInfo = new InterInfo();
        this.medium = new Medium();
        this.mediumList = new MediumList();
        this.pig = new Pig();
        this.pigList = new PigList();
        this.pipe = new Pipe();
        this.pipeList = new PipeList();
        this.pump = new Pump();
        this.pumpList = new PumpList();
        this.siteInfo = new SiteInfo();
        this.valve = new Valve();
        this.valveList = new ValveList();
        this.variableParameter = new VariableParameter();
        this.station = new Station();
        this.stationList = new StationList();
    }
}
