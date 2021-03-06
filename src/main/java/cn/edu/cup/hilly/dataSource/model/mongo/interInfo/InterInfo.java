package cn.edu.cup.hilly.dataSource.model.mongo.interInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@ToString
@Data
@AllArgsConstructor
@Document
public class InterInfo {
    @Id
    private String _id;

    @Indexed(unique = true)
    private String hillyId;

    private ErrorInfo errorInfo;
    private GeneralInfo generalInfo;
    private WarnInfo warnInfo;
    private PigHeadDistance pigHeadDistance;
    private PumpStartFrequence pumpStartFrequence;
    private PumpStartTime pumpStartTime;

    public InterInfo() {
        this.errorInfo = new ErrorInfo();
        this.generalInfo = new GeneralInfo();
        this.warnInfo = new WarnInfo();
        this.pigHeadDistance = new PigHeadDistance();
        this.pumpStartFrequence = new PumpStartFrequence();
        this.pumpStartTime = new PumpStartTime();
    }
}
