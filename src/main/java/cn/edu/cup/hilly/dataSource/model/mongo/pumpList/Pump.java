package cn.edu.cup.hilly.dataSource.model.mongo.pumpList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class Pump {

    private PumpId pumpId;
    private PumpName pumpName;
    private FlowRate flowRate;
    private WaterHead waterHead;
    private W w;
    private Rev rev;
    private StartTime startTime;
    private PumpType pumpType;
    private PumpState pumpState;

    public Pump() {
        this.pumpId = new PumpId();
        this.pumpName = new PumpName();
        this.flowRate = new FlowRate();
        this.waterHead = new WaterHead();
        this.w = new W();
        this.rev = new Rev();
        this.startTime = new StartTime();
        this.pumpType = new PumpType();
        this.pumpState = new PumpState();
    }
}
