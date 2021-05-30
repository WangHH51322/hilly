package cn.edu.cup.hilly.calculate.hilly.large;

import cn.edu.cup.base.IOElement;
import cn.edu.cup.base.InputField;

@IOElement(name = "pig")
public class Pig{

    @InputField(name = "pigName", unit = "")
    String pigName;

    @InputField(name = "pigMass", unit = "kg")
    double pigM;

    @InputField(name = "pigExtra", unit = "")
    double pigE;


    public double getPigM() {
        return pigM;
    }

    public void setPigM(double pigM) {
        this.pigM = pigM;
    }

    public double getPigE() {
        return pigE;
    }

    public void setPigE(double pigE) {
        this.pigE = pigE;
    }

    public String getPigName() {
        return pigName;
    }

    public void setPigName(String pigName) {
        this.pigName = pigName;
    }
}