package axisallies.nations;

public enum NationType {
    USSR("USSR"),
    USA("USA"),
    GERMANY("GERMANY"),
    JAPAN("JAPAN"),
    UK("UK");

    private final String nationTypeString;

    private NationType(String nationTypeString) {
        this.nationTypeString = nationTypeString;
    }

    public NationType getNationType(String nationTypeString) {
        return NationType.valueOf(nationTypeString);
    }

    public String getNationTypeString() {
        return this.nationTypeString;
    }
}