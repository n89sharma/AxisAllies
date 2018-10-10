package axisallies.nations;

import java.util.HashSet;
import java.util.Set;

import axisallies.GameEntity;
import axisallies.board.Territory;
import axisallies.units.Unit;

public class Nation extends GameEntity {

    private NationType nationType;
    private Set<Territory> territories = new HashSet<>();
    private Set<Unit> units = new HashSet<>();
    private Set<Unit> mobilizationZoneUnits = new HashSet<>();
    private int ipc = 0;

    public Nation(NationType nationType) {
        this.nationType = nationType;
    }

    public void addTerritory(Territory territory) {
        this.territories.add(territory);
    }

    public void addUnit(Unit unit) {
        this.units.add(unit);
    }

    public NationType getNationType() {
        return nationType;
    }

    public void setNationType(NationType nationType) {
        this.nationType = nationType;
    }

    public Set<Territory> getTerritories() {
        return territories;
    }

    public void setTerritories(Set<Territory> territories) {
        this.territories = territories;
    }

    public Set<Unit> getUnits() {
        return units;
    }

    public void setUnits(Set<Unit> units) {
        this.units = units;
    }

    public Set<Unit> getMobilizationZoneUnits() {
        return mobilizationZoneUnits;
    }

    public void setMobilizationZoneUnits(Set<Unit> mobilizationZoneUnits) {
        this.mobilizationZoneUnits = mobilizationZoneUnits;
    }

    public int getIpc() {
        return ipc;
    }

    public void setIpc(int ipc) {
        this.ipc = ipc;
    }
}