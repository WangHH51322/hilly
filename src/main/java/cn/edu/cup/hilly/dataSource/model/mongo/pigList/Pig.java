package cn.edu.cup.hilly.dataSource.model.mongo.pigList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class Pig {
    private PigId pigId;
//    private PigArea pigArea;
    private PigName pigName;
//    private PigDiameter pigDiameter;
    private PigMass pigMass;
    private PigExtra pigExtra;

    public Pig() {
        this.pigId = new PigId();
//        this.pigArea = new PigArea();
        this.pigName = new PigName();
//        this.pigDiameter = new PigDiameter();
        this.pigMass = new PigMass();
        this.pigExtra = new PigExtra();
    }
}
