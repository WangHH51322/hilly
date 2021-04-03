package cn.edu.cup.hilly.dataSource.model.mongo.variableParameter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class VariableParameter {

    private Sections sections;
    private SimulationSteps simulationSteps;
    private TimeStep timeStep;
    private TotalTime totalTime;

    public VariableParameter() {
        this.sections = new Sections();
        this.simulationSteps = new SimulationSteps();
        this.timeStep = new TimeStep();
        this.totalTime = new TotalTime();
    }
}
