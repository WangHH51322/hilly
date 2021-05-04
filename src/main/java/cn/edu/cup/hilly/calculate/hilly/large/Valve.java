package cn.edu.cup.hilly.calculate.hilly.large;

import cn.edu.cup.base.IOElement;
import cn.edu.cup.base.InputField;

@IOElement(name = "valve")
public class Valve  {
    @InputField(name = "valveName", unit = "m")
    private String valveName;
    @InputField(name = "valveD", unit = "m")
    private double valveD;
    @InputField(name = "valveK", unit = "")
    private double valveK;
    @InputField(name = "valveC", unit = "")
    private double valveC;
    @InputField(name = "valveT", unit = "s")
    private double valveT;
    @InputField(name = "valveS", unit = "m")
    private double valveS;
    @InputField(name = "valveType", unit = "")
    private double valveType;

    public double getValveD() {
        return valveD;
    }

    public void setValveD(double valveD) {
        this.valveD = valveD;
    }

    public double getValveK() {
        return valveK;
    }

    public void setValveK(double valveK) {
        this.valveK = valveK;
    }

    public double getValveC() {
        return valveC;
    }

    public void setValveC(double valveC) {
        this.valveC = valveC;
    }

    public double getValveT() {
        return valveT;
    }

    public void setValveT(double valveT) {
        this.valveT = valveT;
    }

    public double getValveS() {
        return valveS;
    }

    public void setValveS(double valveS) {
        this.valveS = valveS;
    }

    public double getValveType() {
        return valveType;
    }

    public void setValveType(double valveType) {
        this.valveType = valveType;
    }

    public String getValveName() {
        return valveName;
    }

    public void setValveName(String valveName) {
        this.valveName = valveName;
    }
}

