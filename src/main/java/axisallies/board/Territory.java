package axisallies.board;

import axisallies.nations.NationType;
import axisallies.nations.NationType.TeamType;
import axisallies.units.Unit;
import java.util.HashSet;
import java.util.Set;

public class Territory {

    private String territoryName;
    private NationType nationType;
    private int IPC;
    private TerritoryType territoryType;
    private TeamType teamType;
    private Set<Unit> units = new HashSet<>();

    public Territory(String territoryName, TerritoryType territoryType, NationType nationType, int IPC) {
        this.territoryName = territoryName;
        this.nationType = nationType;
        this.IPC = IPC;
        this.teamType = null != this.nationType ? nationType.getTeamType() : null;
    }

    public String getTerritoryName(){
        return this.territoryName;
    }

    public NationType getNationType() {
        return this.nationType;
    }

    public int getIpc() {
        return IPC;
    }

    public TerritoryType getTerritoryType() {
        return this.territoryType;
    }

    public TeamType getTeamType() {
        return this.teamType;
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

    public void addUnit(Unit unit) {
        units.add(unit);
    }

    public Set<Unit> getUnits() {
        return units;
    }

    public boolean isSeaTerritory() {
        return territoryType.equals(TerritoryType.SEA);
    }

    public boolean isLandTerritory() {
        return territoryType.equals(TerritoryType.LAND);
    }

    public boolean isNeutralType() {
        return territoryType.equals(TerritoryType.NEUTRAL);
    }

    public enum TerritoryType {
        SEA, LAND, NEUTRAL;
    }

}
