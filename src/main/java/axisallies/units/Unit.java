package axisallies.units;

import static axisallies.board.TerritoryType.AIR;
import static axisallies.board.TerritoryType.LAND;
import static axisallies.board.TerritoryType.SEA;

import axisallies.board.Territory;
import axisallies.nations.NationType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Unit {
    
    private UnitType unitType;
    private int travelledDistance = 0;
    private NationType nationType;
    private Territory territory;

    public Unit(UnitType unitType) {
        this.unitType = unitType;
    }

    public Unit(UnitType unitType, NationType nationType) {
        this.unitType = unitType;
        this.nationType = nationType;
    }

    public Unit(UnitType unitType, NationType nationType, Territory territory) {
        this.unitType = unitType;
        this.nationType = nationType;
        this.territory = territory;
    }

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
        return this.unitType.equals(unitType);
    }
}
