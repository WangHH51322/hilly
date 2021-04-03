package cn.edu.cup.hilly.calculate.hilly.large;

import cn.edu.cup.base.IOElement;
import cn.edu.cup.base.InputField;

@IOElement(name = "pipe")
public class Pipeline {

    @InputField(name = "pipeOutDiameter", unit = "m")
    private double diameter;
    @InputField(name = "pipeThickness", unit = "m")
    private double thinkness;
    @InputField(name = "pipeRoughness", unit = "m")
    private double roughness;

    public Pipeline() {
    }
    // 自动生成的代码略

    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    public double getThinkness() {
        return thinkness;
    }

    public void setThinkness(double thinkness) {
        this.thinkness = thinkness;
    }

    public double getRoughness() {
        return roughness;
    }

    public void setRoughness(double roughness) {
        this.roughness = roughness;
    }


}