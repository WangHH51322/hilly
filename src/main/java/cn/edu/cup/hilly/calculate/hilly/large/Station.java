package cn.edu.cup.hilly.calculate.hilly.large;

import cn.edu.cup.base.IOElement;
import cn.edu.cup.base.InputField;

@IOElement(name = "siteInfo")
public class Station {

    @InputField(name = "siteKeyName", unit = "")
    private String stationName[];

    @InputField(name = "siteKeyMileage", unit = "km")
    private double stationL[];

    @InputField(name = "siteKeyAltitude", unit = "m")
    private double stationZ[];


    public String[] getStationName() {
        return stationName;
    }

    public void setStationName(String[] stationName) {
        this.stationName = stationName;
    }

    public double[] getStationL() {
        return stationL;
    }

    public void setStationL(double[] stationL) {
        this.stationL = stationL;
    }

    public double[] getStationZ() {
        return stationZ;
    }

    public void setStationZ(double[] stationZ) {
        this.stationZ = stationZ;
    }
}
