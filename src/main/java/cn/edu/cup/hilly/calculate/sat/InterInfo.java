package cn.edu.cup.hilly.calculate.sat;

import cn.edu.cup.base.IOElement;
import cn.edu.cup.base.InputField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@IOElement(name = "InterInfo")
public class InterInfo {

    @InputField(name = "pumpStartFrequence", unit = "")
    private String pumpStartFrequence;
    @InputField(name = "warnInfo", unit = "")
    private String warnInfo;
    @InputField(name = "errorInfo", unit = "")
    private String errorInfo;
    @InputField(name = "pigHeadDistance", unit = "")
    private String pigHeadDistance;
    @InputField(name = "generalInfo", unit = "")
    private String generalInfo;
    @InputField(name = "pumpStartTime", unit = "s")
    private String pumpStartTime;
}
