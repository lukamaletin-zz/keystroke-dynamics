package rs.ac.uns.ftn.ori.keystrokelogger;

public class Sample {

    private double[] travelTimes;
    private double[] pressures;
    private double[] areas;
    private double[] holdTimes;

    private int travelIndex = 0;
    private int pressureIndex = 0;
    private int areaIndex = 0;
    private int holdIndex = 0;

    public Sample() {
        travelTimes = new double[4];
        pressures = new double[5];
        areas = new double[5];
        holdTimes = new double[5];
    }

    public void addTravelTime(double time) {
        travelTimes[travelIndex++] = time;
    }

    public void addPressure(double pressure) {
        pressures[pressureIndex++] = pressure;
    }

    public void addArea(double area) {
        areas[areaIndex++] = area;
    }

    public void addHoldTime(double time) {
        holdTimes[holdIndex++] = time;
    }

    public double[] getTravelTimes() {
        return travelTimes;
    }

    public void setTravelTimes(double[] travelTimes) {
        this.travelTimes = travelTimes;
    }

    public double[] getPressures() {
        return pressures;
    }

    public void setPressures(double[] pressures) {
        this.pressures = pressures;
    }

    public double[] getAreas() {
        return areas;
    }

    public void setAreas(double[] areas) {
        this.areas = areas;
    }

    public double[] getHoldTimes() {
        return holdTimes;
    }

    public void setHoldTimes(double[] holdTimes) {
        this.holdTimes = holdTimes;
    }
}
