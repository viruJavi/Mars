package model;

public enum Orientation {
    N("North"),
    S("South"),
    W("West"),
    E("East");

    public final String value;

    Orientation(String value) {
        this.value = value;
    }

}
