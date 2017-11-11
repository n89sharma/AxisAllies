package axisallies.units;

import axisallies.board.Territory;
import axisallies.nations.NationType;
import lombok.Data;
import lombok.NoArgsConstructor;

import static axisallies.board.TerritoryType.LAND;
import static axisallies.board.TerritoryType.SEA;
import static axisallies.units.UnitType.AIRCRAFT_CARRIER;
import static axisallies.units.UnitType.TRANSPORT;

@Data
@NoArgsConstructor
public class Unit {

    private UnitType unitType;
    private int travelledDistance = 0;
    private NationType nationType;
    private Territory territory;

    public static Unit buildUnitOfNation(UnitType unitType, NationType nationType) {

        return (unitType.equals(AIRCRAFT_CARRIER) || unitType.equals(TRANSPORT)) ?
                new CarrierUnit(unitType, nationType) :
                new Unit(unitType, nationType);
    }

    public static Unit buildUnitOfNationAtTerritory(
            UnitType unitType,
            NationType nationType,
            Territory territory) {

        return (unitType.equals(AIRCRAFT_CARRIER) || unitType.equals(TRANSPORT)) ?
                new CarrierUnit(unitType, nationType, territory) :
                new Unit(unitType, nationType, territory);

    }

    protected Unit(UnitType unitType, NationType nationType) {
        this.unitType = unitType;
        this.nationType = nationType;
    }

    protected Unit(UnitType unitType, NationType nationType, Territory territory) {
        this.unitType = unitType;
        this.nationType = nationType;
        this.territory = territory;
    }

    public boolean isSeaUnit() {
        return unitType.getTerritoryType().equals(SEA);
    }

    public boolean isLandUnit() {
        return unitType.getTerritoryType().equals(LAND);
    }

    public boolean isType(UnitType unitType) {
        return this.unitType.equals(unitType);
    }
}
