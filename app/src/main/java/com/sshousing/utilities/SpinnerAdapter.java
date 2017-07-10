package com.sshousing.utilities;

/**
 * Created by ilyas on 7/9/17.
 */

public class SpinnerAdapter {

    private int id;
    private String value;

    public SpinnerAdapter(int id, String value) {
        this.id = id;
        this.value = value;
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
