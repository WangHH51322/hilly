package cn.edu.cup.hilly.calculate.hilly.large;

import cn.edu.cup.base.IOElement;
import cn.edu.cup.base.InputField;

@IOElement(name = "pig")
public class Pig{

    @InputField(name = "pigName", unit = "")
    String pigName;

    @InputField(name = "pigMass", unit = "kg")
    double pigM;

    @InputField(name = "pigDiameter", unit = "m")
    double pigD;

    @InputField(name = "pigArea", unit = "m^2")
    double pigA;


    public double getPigM() {
        return pigM;
    }

    public void setPigM(double pigM) {
        this.pigM = pigM;
    }

    public double getPigD() {
        return pigD;
    }

    public void setPigD(double pigD) {
        this.pigD = pigD;
    }

    public double getPigA() {
        return pigA;
    }

    public void setPigA(double pigA) {
        this.pigA = pigA;
    }

    public String getPigName() {
        return pigName;
    }

    public void setPigName(String pigName) {
        this.pigName = pigName;
    }
}