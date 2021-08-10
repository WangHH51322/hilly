package cn.edu.cup.hilly.dataSource.model.mongo.valveList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class Valve {
    private ValveId valveId;
    private ValveD valveD;
    private ValveK valveK;
    private ValveKv valveKv;
    private ValvePercent valvePercent;
    private ValveT valveT;
    private ValveName valveName;
    private ValveType valveType;
    private ValveS valveS;
    private ValveC valveC;

    public Valve() {
        this.valveId = new ValveId();
        this.valveD = new ValveD();
        this.valveK = new ValveK();
        this.valveKv = new ValveKv();
        this.valvePercent = new ValvePercent();
        this.valveT = new ValveT();
        this.valveName = new ValveName();
        this.valveType = new ValveType();
        this.valveS = new ValveS();
        this.valveC = new ValveC();
    }
}
