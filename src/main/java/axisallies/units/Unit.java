package axisallies.units;

import axisallies.board.Territory;
import axisallies.nations.NationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import static axisallies.board.TerritoryType.*;
import static axisallies.units.UnitType.AIRCRAFT_CARRIER;
import static axisallies.units.UnitType.TRANSPORT;

@Getter
@Setter
@ToString
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

    public boolean isAirUnit() {
        return unitType.getTerritoryType().equals(AIR);
    }

    public boolean isType(UnitType unitType) {
        return this.unitType.equals(unitType);
    }

    public Integer getAttack() {
        return unitType.getAttackStrength();
    }

    public Integer getDefense() {
        return unitType.getDefenseStrength();
    }
}
