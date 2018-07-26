package com.sshousing.utilities;

/**
 * Created by ilyas on 7/9/17.
 */

public class SpinnerAdapter {

    private int id;
    private String value;
    private int floors;

    public SpinnerAdapter(int id, String value, int floors) {
        this.id = id;
        this.value = value;
        this.floors = floors;
    }

    public int getFloors() {
        return floors;
    }

    public void setFloors(int floors) {
        this.floors = floors;
    }

    public String getValue() {

        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return value;
    }
}
