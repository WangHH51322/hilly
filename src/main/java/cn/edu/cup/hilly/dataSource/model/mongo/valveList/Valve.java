package cn.edu.cup.hilly.dataSource.model.mongo.valveList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class Valve {
    private ValveId valveId;
    private ValveDiameter valveDiameter;
    private ValveOpen valveOpen;
    private ValveStartTime valveStartTime;
    private ValveName valveName;
    private ValveType valveType;
    private ValveLocation valveLocation;
    private ValveCoefficient valveCoefficient;

    public Valve() {
        this.valveId = new ValveId();
        this.valveDiameter = new ValveDiameter();
        this.valveOpen = new ValveOpen();
        this.valveStartTime = new ValveStartTime();
        this.valveName = new ValveName();
        this.valveType = new ValveType();
        this.valveLocation = new ValveLocation();
        this.valveCoefficient = new ValveCoefficient();
    }
}
