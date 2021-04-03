package cn.edu.cup.hilly.calculate.sat;

import cn.edu.cup.base.IOElement;
import cn.edu.cup.base.InputField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@IOElement(name = "hilly01")
public class Project {

    @InputField(name = "interInfo", unit = "")
    private InterInfo interInfo;

    public void run() {
        System.out.println(interInfo);
    }
}
