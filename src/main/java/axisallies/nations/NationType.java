package axisallies.nations;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonValue;

public enum NationType {
    USSR("USSR"),
    USA("USA"),
    GERMANY("GERMANY"),
    JAPAN("JAPAN"),
    UK("UK");

    private final String name;

    NationType(String name) {
        this.name = name;
    }

    @JsonValue
    public String jsonValue() {
        return this.name;
    }
}