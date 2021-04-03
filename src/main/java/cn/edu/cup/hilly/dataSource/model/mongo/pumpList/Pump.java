package cn.edu.cup.hilly.dataSource.model.mongo.pumpList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class Pump {

    private PumpId pumpId;
    private PumpName pumpName;
    private PumpFlow pumpFlow;
    private PumpHead pumpHead;
    private PumpPower pumpPower;
    private PumpSpeed pumpSpeed;

    public Pump() {
        this.pumpId = new PumpId();
        this.pumpName = new PumpName();
        this.pumpFlow = new PumpFlow();
        this.pumpHead = new PumpHead();
        this.pumpPower = new PumpPower();
        this.pumpSpeed = new PumpSpeed();
    }
}
