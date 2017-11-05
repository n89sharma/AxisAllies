package axisallies.nations;

import lombok.Getter;

import static axisallies.nations.TeamType.ALLIES;
import static axisallies.nations.TeamType.AXIS;

@Getter
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
}