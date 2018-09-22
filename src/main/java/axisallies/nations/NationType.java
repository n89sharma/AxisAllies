package axisallies.nations;

import static axisallies.nations.TeamType.ALLIES;
import static axisallies.nations.TeamType.AXIS;

public enum NationType {

    // @formatter:off
    USSR("USSR", ALLIES),
    USA("USA", ALLIES),
    GERMANY("GERMANY", AXIS),
    JAPAN("JAPAN", AXIS),
    UK("UK", ALLIES);
    // @formatter:on

    private final String nationTypeString;
    private final TeamType teamType;

    NationType(String nationTypeString, TeamType teamType) {
        this.nationTypeString = nationTypeString;
        this.teamType = teamType;
    }

    public String getNationTypeString() {
        return nationTypeString;
    }

    public TeamType getTeamType() {
        return teamType;
    }
}