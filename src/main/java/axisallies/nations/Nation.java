package axisallies.nations;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import axisallies.board.Territory;
import axisallies.units.Unit;
import axisallies.units.UnitType;

public class Nation {

    private NationType nationType;
    private Set<Territory> territories;
    private Set<Unit> units;
    private int treasury=0;
    private Set<Unit> mobilizationZoneUnits = new HashSet<>();

    public Nation(NationType nationType, Set<Territory> territories, Set<Unit> units) {
        this.nationType = nationType;
        this.territories = territories;
        this.units = units;
        addToTreasury();
    }

    public Set<Territory> getTerritories() {
        return territories;
    }

    public Set<Unit> getUnits() {
        return units;
    }

    public int ipcCalculationBasedOnTerritoriesOwned() {
        return getTerritories().stream()
            .mapToInt(Territory::getIpc)
            .sum();
    }

    public void addToTreasury() {
        treasury += ipcCalculationBasedOnTerritoriesOwned();
    }

    public int getTreasuryAmount() {
        return treasury;
    }

    public void purchaseUnits(Map<UnitType, Integer> unitOrder) {
        int orderCost = unitOrder.entrySet()
            .stream()
            .mapToInt(entry -> entry.getKey().getProductionCost()*entry.getValue())
            .sum();

        if(orderCost <= treasury) {
            for(Entry<UnitType, Integer> entry : unitOrder.entrySet()) {
                for(int i=0; i<entry.getValue(); i++) {
                    mobilizationZoneUnits.add(new Unit("", nationType, entry.getKey()));
                }
            }
            treasury -= orderCost;
        }
    }

    public Set<Unit> getMobilizationUnit() {
        return mobilizationZoneUnits;
    }
}