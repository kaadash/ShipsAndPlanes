package helpers;

/**
 * Created by kadash on 09.01.16.
 */
public class MutableDouble {
    private Double value;

    public MutableDouble(){}

    public MutableDouble(double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value != null ? value.toString() : "null";
    }
}
