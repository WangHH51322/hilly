package cn.edu.cup.hilly.calculate.hilly.large;

import cn.edu.cup.base.IOElement;
import cn.edu.cup.base.InputField;

import java.util.ArrayList;
import java.util.List;

@IOElement(name = "站点列表")
public class Stations {
    @InputField(name = "站点名称", unit = "")
    private String stationName;
    @InputField(name = "站点位置", unit = "m")
    private double stationL;
    @InputField(name = "站点类型", unit = "")
    private double stationType;
    @InputField(name = "站点泵", unit = "")
    private List<Pump> pumps = new ArrayList<>();
    @InputField(name = "站点阀", unit = "")
    private List<Valve> valves = new ArrayList<>();




    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public double getStationL() {
        return stationL;
    }

    public void setStationL(double stationL) {
        this.stationL = stationL;
    }

    public double getStationType() {
        return stationType;
    }

    public void setStationType(double stationType) {
        this.stationType = stationType;
    }


}
