package cn.edu.cup.hilly.calculate.hilly.large;

import cn.edu.cup.base.IOElement;
import cn.edu.cup.base.InputField;

@IOElement(name = "medium")
public class Oil {
    @InputField(name = "mediumName", unit = "")
    String fluidname;
    @InputField(name = "mediumDensity", unit = "kg/m3")
    double density;
    @InputField(name = "mediumViscosity", unit = "mPa.s")
    double viscosity;
    @InputField(name = "mediumTemperature", unit = "K")
    double temperature;

    public double getDensity() {
        return density;
    }

    public void setDensity(double density) {
        this.density = density;
    }

    public double getViscosity() {
        return viscosity;
    }

    public void setViscosity(double viscosity) {
        this.viscosity = viscosity;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getFluidname() {
        return fluidname;
    }

    public void setFluidname(String fluidname) {
        this.fluidname = fluidname;
    }
}