package com.somemistake.calculator.model;

public class Data {
    private String number;
    private int fromSystem;
    private int toSystem;

    public Data() {
    }

    public Data(String number, int fromSystem, int toSystem) {
        this.number = number;
        this.fromSystem = fromSystem;
        this.toSystem = toSystem;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getFromSystem() {
        return fromSystem;
    }

    public void setFromSystem(int fromSystem) {
        this.fromSystem = fromSystem;
    }

    public int getToSystem() {
        return toSystem;
    }

    public void setToSystem(int toSystem) {
        this.toSystem = toSystem;
    }
}
