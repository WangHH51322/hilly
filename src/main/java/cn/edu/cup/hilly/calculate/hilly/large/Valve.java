package cn.edu.cup.hilly.calculate.hilly.large;

import cn.edu.cup.base.IOElement;
import cn.edu.cup.base.InputField;

@IOElement(name = "阀门")
public class Valve  {
    @InputField(name = "阀门名称", unit = "m")
    private String valvename;
    @InputField(name = "直径", unit = "m")
    private double valveD;
    @InputField(name = "开度", unit = "")
    private double valveK;
    @InputField(name = "阀门系数", unit = "")
    private double valveC;
    @InputField(name = "启动时间", unit = "s")
    private double valveT;
    @InputField(name = "阀门位置", unit = "m")
    private double valveS;
    @InputField(name = "阀门类型", unit = "")
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
}

