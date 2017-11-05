package axisallies.units;

import axisallies.board.Territory;
import axisallies.nations.NationType;
import lombok.AllArgsConstructor;
import lombok.Data;

import static axisallies.board.TerritoryType.*;

@Data
@AllArgsConstructor
public class Unit {

    private Territory territory;
    private NationType nationType;
    private UnitType unitType;
    private String territoryName;

    public boolean isHostileTo(Unit unit) {
        return nationType.getTeamType().isHostileTo(unit.getNationType().getTeamType());
    }

    public boolean isFriendlyTo(Unit unit) {
        return !isHostileTo(unit);
    }

    public boolean isSeaUnit() {
        return unitType.getTerritoryType().equals(SEA);
    }

    public boolean isAirUnit() {
        return unitType.getTerritoryType().equals(AIR);
    }

    public boolean isLandUnit() {
        return unitType.getTerritoryType().equals(LAND);
    }

    public boolean isType(UnitType unitType) {
        return unitType.equals(unitType);
    }
}
