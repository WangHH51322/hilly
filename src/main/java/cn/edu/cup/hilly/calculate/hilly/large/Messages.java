package cn.edu.cup.hilly.calculate.hilly.large;


import cn.edu.cup.base.IOElement;
import cn.edu.cup.base.InputField;

@IOElement(name = "message")
public class Messages {
    @InputField(name = "startPumpOrderTime", unit = "s")
    double startPumpTime;
    @InputField(name = "startPumpOrderRev", unit = "")
    double startPumpRev;
    @InputField(name = "startPumpOrderDis", unit = "km")
    double startPigDistance;

    @InputField(name = "errorTips", unit = "")
    String error;
    @InputField(name = "warnTips", unit = "")
    String warn;
    @InputField(name = "infoTips", unit = "")
    String info;

    public double getStartPumpTime() {
        return startPumpTime;
    }

    public void setStartPumpTime(double startPumpTime) {
        this.startPumpTime = startPumpTime;
    }

    public double getStartPumpRev() {
        return startPumpRev;
    }

    public void setStartPumpRev(double startPumpRev) {
        this.startPumpRev = startPumpRev;
    }

    public double getStartPigDistance() {
        return startPigDistance;
    }

    public void setStartPigDistance(double startPigDistance) {
        this.startPigDistance = startPigDistance;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getWarn() {
        return warn;
    }

    public void setWarn(String warn) {
        this.warn = warn;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
