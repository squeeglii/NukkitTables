package dev.cg360.mc.nukkittables.math;

public class FloatRange {

    protected float min;
    protected float max;

    public FloatRange (float min, float max){
        this.min = min;
        this.max = max;
    }

    public boolean isWithinRange(float value){ return isWithinRange(value, true, true); }
    public boolean isWithinRange(float value, boolean inclusiveMin, boolean inclusiveMax){
        boolean minCheck = inclusiveMin ? value >= min : value > min;
        boolean maxCheck = inclusiveMax ? value <= max : value < max;
        return minCheck && maxCheck;
    }

    public float getMin() { return min; }
    public float getMax() { return max; }
}
