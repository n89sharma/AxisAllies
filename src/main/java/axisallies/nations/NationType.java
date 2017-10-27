package axisallies.nations;

public enum NationType {
    USSR("USSR"),
    USA("USA"),
    GERMANY("GERMANY"),
    JAPAN("JAPAN"),
    UK("UK");

    private final String name;

    private NationType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}