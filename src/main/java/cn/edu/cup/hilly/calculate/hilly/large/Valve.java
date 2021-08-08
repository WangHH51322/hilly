package cn.edu.cup.hilly.calculate.hilly.large;

import cn.edu.cup.base.IOElement;
import cn.edu.cup.base.InputField;

@IOElement(name = "valve")
public class Valve  {
    @InputField(name = "valveName", unit = "")
    private String valveName;//阀门名称
    @InputField(name = "valveD", unit = "m")
    private double valveD;//阀门直径
    @InputField(name = "valveK", unit = "")
    private double valveK;//阀门实时开度
    @InputField(name = "valveC", unit = "")
    private double valveC;//阀门实时Kv值
    @InputField(name = "valveT", unit = "s")
    private double valveT;//阀门开启所用时间
    @InputField(name = "valveS", unit = "m")
    private double valveS;//阀门位置
    @InputField(name = "valveType", unit = "")
    private int valveType;//阀门种类
    @InputField(name = "valveKv", unit = "")
    private double []valveKv;//阀门Kv值，10个值，分别代表开度从0-100%的流量系数

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

    public void setValveType(int valveType) {
        this.valveType = valveType;
    }

    public String getValveName() {
        return valveName;
    }

    public void setValveName(String valveName) {
        this.valveName = valveName;
    }

    public double[] getValveKv() {
        return valveKv;
    }

    public void setValveKv(double[] valveKv) {
        this.valveKv = valveKv;
    }
}

