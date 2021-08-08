package cn.edu.cup.hilly.dataSource.model.mongo.variableParameter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class VariableParameter {

//    private Sections sections;
//    private SimulationSteps simulationSteps;
//    private TimeStep timeStep;
//     private TotalTime totalTime;
    private DesignFlow designFlow;
    private PutPigFlag putPigFlag;
    private WaterFillingLength waterFillingLength;
    private HighOpen highOpen;
    private PressureMode pressureMode;

    public VariableParameter() {
//        this.sections = new Sections();
//        this.simulationSteps = new SimulationSteps();
//        this.timeStep = new TimeStep();
//         this.totalTime = new TotalTime();
        this.designFlow = new DesignFlow();
        this.putPigFlag = new PutPigFlag();
        this.waterFillingLength = new WaterFillingLength();
        this.highOpen = new HighOpen();
        this.pressureMode = new PressureMode();
    }
}
