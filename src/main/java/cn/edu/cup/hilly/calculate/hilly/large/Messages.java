package cn.edu.cup.hilly.calculate.hilly.large;


import cn.edu.cup.base.IOElement;
import cn.edu.cup.base.InputField;

@IOElement(name = "交互信息")
public class Messages {
    @InputField(name = "启泵时间", unit = "s")
    double startPumpTime;
    @InputField(name = "启泵频率", unit = "")
    double startPumpRev;
    @InputField(name = "投放前清管器与水头距离", unit = "km")
    double startPigDistance;

    @InputField(name = "错误提示", unit = "")
    String error;
    @InputField(name = "警告提示", unit = "")
    String warn;
    @InputField(name = "普通提示", unit = "")
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
