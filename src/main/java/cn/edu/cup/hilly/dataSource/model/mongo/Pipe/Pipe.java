package cn.edu.cup.hilly.dataSource.model.mongo.Pipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class Pipe {

//    private PipeStartMileage pipeStartMileage;
//    private PipeLength pipeLength;
//    private PipeMileage pipeMileage;
//    private PipeAltitude pipeAltitude;
    private PipeOutDiameter pipeOutDiameter;
    private PipeRoughness pipeRoughness;
//    private PipeSiteLocation pipeSiteLocation;
//    private PipeStartAltitude pipeStartAltitude;
    private PipeThickness pipeThickness;

    public Pipe() {
//        this.pipeStartMileage = new PipeStartMileage();
//        this.pipeLength = new PipeLength();
//        this.pipeMileage = new PipeMileage();
//        this.pipeAltitude = new PipeAltitude();
        this.pipeOutDiameter = new PipeOutDiameter();
        this.pipeRoughness = new PipeRoughness();
//        this.pipeSiteLocation = new PipeSiteLocation();
//        this.pipeStartAltitude = new PipeStartAltitude();
        this.pipeThickness = new PipeThickness();
    }
}
