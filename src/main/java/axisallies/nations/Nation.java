package axisallies.nations;

import axisallies.board.Territory;
import axisallies.units.Unit;
import axisallies.units.UnitType;
import lombok.Data;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import static axisallies.units.Unit.buildUnitOfNation;

@Data
public class Nation {

    private NationType nationType;
    private Set<Territory> territories= new HashSet<>();
    private Set<Unit> units= new HashSet<>();
    private Set<Unit> mobilizationZoneUnits= new HashSet<>();
    private int ipc =0;

    public Nation(NationType nationType) {
        this.nationType = nationType;
    }

    public void addTerritory(Territory territory){
        this.territories.add(territory);
    }

    public void addUnit(Unit unit) {
        this.units.add(unit);
    }

    private int ipcCalculationBasedOnTerritoriesOwned() {
        return getTerritories().stream()
            .mapToInt(Territory::getIpc)
            .sum();
    }

    private void addToTreasury() {
        ipc += ipcCalculationBasedOnTerritoriesOwned();
    }

    public void purchaseUnits(Map<UnitType, Integer> unitOrder) {
        int orderCost = unitOrder.entrySet()
            .stream()
            .mapToInt(entry -> entry.getKey().getProductionCost()*entry.getValue())
            .sum();

        if(orderCost <= ipc) {
            for(Entry<UnitType, Integer> entry : unitOrder.entrySet()) {
                for(int i=0; i<entry.getValue(); i++) {
                    mobilizationZoneUnits.add(buildUnitOfNation(entry.getKey(), nationType));
                }
            }
            ipc -= orderCost;
        }
    }
}