package cn.edu.cup.hilly.calculate.hilly.large;

import cn.edu.cup.base.IOElement;
import cn.edu.cup.base.InputField;

import java.util.List;

@IOElement(name = "station")
public class Stations {
    @InputField(name = "stationName", unit = "")   //站点名称
    private String stationName;
    @InputField(name = "stationL", unit = "m")      //站点里程
    private double stationL;
    @InputField(name = "stationZ", unit = "m")       //站点高程
    private double stationZ;
    @InputField(name = "stationType", unit = "")     //站点种类，泵站为1、排气点2、阀室3
    private int stationType;
    @InputField(name = "stationPumps", unit = "")     //泵列表
    private List<Pump> pumps;
    @InputField(name = "stationValves", unit = "")    //阀列表
    private List<Valve> valves;




    public List<Pump> getPumps() {
        return pumps;
    }


    public double getStationZ() {
        return stationZ;
    }

    public void setStationZ(double stationZ) {
        this.stationZ = stationZ;
    }



    public void setPumps(List<Pump> pumps) {
        this.pumps = pumps;
    }

    public List<Valve> getValves() {
        return valves;
    }

    public void setValves(List<Valve> valves) {
        this.valves = valves;
    }

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

    public void setStationType(int stationType) {
        this.stationType = stationType;
    }


}
