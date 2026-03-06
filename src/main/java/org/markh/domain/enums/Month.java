package org.markh.domain.enums;

public enum Month {
    JAN(1, "Jan"),
    FEB(2, "Feb"),
    MAR(3, "Mar"),
    APR(4, "Apr"),
    MAY(5, "May"),
    JUN(6, "Jun"),
    JUL(7, "Jul"),
    AUG(8, "Aug"),
    SEP(9, "Sep"),
    OCT(10, "Oct"),
    NOV(11, "Nov"),
    DEC(12, "Dec");

    private final int value;
    private final String display;

    Month(int value, String display) {
        this.value = value;
        this.display = display;
    }

    public int getValue() {
        return value;
    }

    public String getDisplay() {
        return display;
    }

    public static Month fromInt(int value) {
        for (Month m : values()) {
            if (m.value == value) {
                return m;
            }
        }
        throw new IllegalArgumentException("Invalid month: " + value);
    }

    public static Month fromString(String value) {
        for (Month m : values()) {
            if (m.display.equals(value)) {
                return m;
            }
        }
        throw new IllegalArgumentException("Invalid month: " + value);
    }
}
