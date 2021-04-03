package cn.edu.cup.hilly.calculate.hilly.large;

import cn.edu.cup.base.IOElement;
import cn.edu.cup.base.InputField;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

@IOElement(name = "离心泵")
public class Pump{

    @InputField(name = "离心泵名称", unit = "")
    private String pumpname;

    @InputField(name = "流量", unit = "m3/h")
    private double flowrate[];
    @InputField(name = "扬程", unit = "m")
    private double waterhead[];
    @InputField(name = "转速", unit = "r/min")
    private double rev[];
    @InputField(name = "功率", unit = "KW")
    private double W[];
    @InputField(name = "泵状态", unit = "")
    private int state;

    private double head[];

    public void initPumpFunction() {
        int m = flowrate.length;

        WeightedObservedPoints points = new WeightedObservedPoints();
        for (int i = 0; i < m; i++) {
            points.add(flowrate[i], waterhead[i]);
        }
        PolynomialCurveFitter fitter = PolynomialCurveFitter.create(2);
        head = fitter.fit(points.toList());
    }



    public void pumpStation(){


    }


    public String getPumpname() {
        return pumpname;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setPumpname(String pumpname) {
        this.pumpname = pumpname;
    }

    public double[] getFlowrate() {
        return flowrate;
    }

    public void setFlowrate(double[] flowrate) {
        this.flowrate = flowrate;
    }

    public double[] getWaterhead() {
        return waterhead;
    }

    public void setWaterhead(double[] waterhead) {
        this.waterhead = waterhead;
    }

    public double[] getHead() {
        return head;
    }

    public void setHead(double[] head) {
        this.head = head;
    }
}