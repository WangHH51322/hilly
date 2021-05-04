package cn.edu.cup.hilly.calculate.hilly.large;

import cn.edu.cup.base.IOElement;
import cn.edu.cup.base.InputField;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

import static java.lang.Math.pow;

@IOElement(name = "pump")
public class Pump{

    @InputField(name = "pumpName", unit = "")    //泵名称
    private String pumpname;

    @InputField(name = "flowRate", unit = "m3/h")   //流量
    private double flowrate[];
    @InputField(name = "waterHead", unit = "m")     //流量对应的扬程
    private double waterhead[];
    @InputField(name = "rev", unit = "r/min")       //额定转速
    private double rev;
    @InputField(name = "w", unit = "KW")        //额定功率
    private double W;
    @InputField(name = "pumpState", unit = "")   //0为关、1为开，前端不用展示
    private int state;
    @InputField(name = "pumpType", unit = "")       //1为主泵，2为变频泵
    private int pumpType;
    @InputField(name = "startTime", unit = "s")       //泵的启动耗时（0-600s）
    private int startTime;

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


    public void stopPump(){
        double GDd=0.33*887.5*pow((0.7355*rev/W),1.435);//电机转动惯量计算
        double GDw=0.33*887.5*pow((0.7355*rev/W),1.435);//电机流体转动惯量计算
        double GDz=0.33*887.5*pow((0.7355*rev/W),1.435);//总转动惯量计算

    }


    public void pumpStation(){


    }

    public int getPumpType() {
        return pumpType;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public void setPumpType(int pumpType) {
        this.pumpType = pumpType;
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

    public double getRev() {
        return rev;
    }

    public void setRev(double rev) {
        this.rev = rev;
    }

    public double getW() {
        return W;
    }

    public void setW(double w) {
        W = w;
    }

    public void setHead(double[] head) {
        this.head = head;
    }
}