package axisallies.nations;

public enum NationType {
    USSR    ("USSR"     , TeamType.ALLIES),
    USA     ("USA"      , TeamType.ALLIES),
    GERMANY ("GERMANY"  , TeamType.AXIS),
    JAPAN   ("JAPAN"    , TeamType.AXIS),
    UK      ("UK"       , TeamType.ALLIES);

    private final String name;
    private final TeamType teamType;

    private NationType(String name, TeamType teamType) {
        this.name = name;
        this.teamType = teamType;
    }

    public String getName() {
        return this.name;
    }

    public TeamType getTeamType(){
        return this.teamType;
    }

    public enum TeamType {
        AXIS, ALLIES;
    }
}