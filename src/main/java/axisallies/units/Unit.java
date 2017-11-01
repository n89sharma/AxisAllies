package axisallies.units;

import axisallies.nations.NationType;
import axisallies.nations.NationType.TeamType;
import java.util.List;

import static axisallies.units.UnitType.UnitTerrainType.AIR;
import static axisallies.units.UnitType.UnitTerrainType.LAND;
import static axisallies.units.UnitType.UnitTerrainType.SEA;

import axisallies.board.Territory;
import axisallies.gameplay.GamePhaseType;

public class Unit {

    private String territory;
    private NationType nationType;
    private UnitType unitType;
    private int currentRange;
    private TeamType teamType;

    public Unit() {

    }

    public Unit(String territory, NationType nationType, UnitType unitType) {
        this.territory = territory;
        this.nationType = nationType;
        this.unitType = unitType;
        this.currentRange = unitType.getMovementRange();
        this.teamType = this.nationType.getTeamType();
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public String getTerritory() {
        return territory;
    }

    public int getCurrentRange() {
        return currentRange;
    }

    public TeamType getTeamType() {
        return teamType;
    }

    public boolean isSeaUnit() {
        return unitType.getUnitTerrainType().equals(SEA);
    }

    public boolean isLandUnit() {
        return unitType.getUnitTerrainType().equals(LAND);
    }

    public boolean isAirUnit() {
        return unitType.getUnitTerrainType().equals(AIR);
    }

    public boolean is(UnitType unitType) {
        return unitType.equals(unitType);
    }

    public boolean isHostileTo(Unit unit) {
        boolean hostileTo = false;
        if(null != teamType) {
            hostileTo = !unit.getTeamType().equals(teamType);   //if unit team does not match territroy team then it is hostile
        }
        return hostileTo;
    }

    public boolean isFriendlyTo(Unit unit) {
        return !isHostileTo(unit);
    }
}
