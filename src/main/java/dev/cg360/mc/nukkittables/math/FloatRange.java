package dev.cg360.mc.nukkittables.math;

public class FloatRange {

    protected float min;
    protected float max;

    public FloatRange (float min, float max){
        if(min <= max) {
            this.min = min;
            this.max = max;
        } else {
            //Inverse for if someone has them the wrong way round.
            this.min = max;
            this.max = min;
        }
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
