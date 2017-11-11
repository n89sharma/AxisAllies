package axisallies.units;

import axisallies.board.Territory;
import axisallies.board.TerritoryType;
import axisallies.nations.NationType;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static axisallies.board.Board.areFriendly;
import static axisallies.board.Board.areHostile;
import static axisallies.board.TerritoryType.SEA;
import static java.util.stream.Collectors.toSet;

@NoArgsConstructor
public class Company {

    private Set<Unit> units = new HashSet<>();
    private Set<CarrierUnit> carrierUnits = new HashSet<>();

    public static Company buildCompany(NationType nationType, UnitType ... unitTypes) {
        Company company  = new Company();
        Set<Unit> units = Arrays.stream(unitTypes)
            .map(Unit::buildUnit)
            .collect(toSet());
        units.stream()
            .forEach(u -> u.setNationType(nationType));
        company.setUnits(units);
        return company;
    }

    public Company(Unit ... units) {
        this.units = Arrays.stream(units).collect(toSet());
    }

    public void setUnits(Set<Unit> units) {
        this.units = units;
    }

    public Set<Unit> getUnits() {
        return units;
    }

    public void addUnit(CarrierUnit unit) {
        carrierUnits.add(unit);
    }

    public void addUnit(Unit unit) {
        units.add(unit);
    }

    public boolean hasType(UnitType unitType) {
        boolean unitsHasType = units.stream()
            .anyMatch(unit -> unit.getUnitType().equals(unitType));
        boolean carrierUnitsHasType = carrierUnits.stream()
            .anyMatch(carrierUnit -> carrierUnit.getUnitType().equals(unitType));    
        return unitsHasType || carrierUnitsHasType;
    }

    public boolean areAllOfType(TerritoryType territoryType) {
        boolean unitsAreOfGivenType = units.stream()
            .allMatch(unit -> unit.getUnitType().getTerritoryType().equals(territoryType));
        boolean carrierUnitsAreOfGivenType = carrierUnits.stream()
            .allMatch(unit -> unit.getUnitType().getTerritoryType().equals(territoryType));
        return unitsAreOfGivenType && carrierUnitsAreOfGivenType;
    }

    public boolean areAllSeaUnits() {
        return areAllOfType(SEA);
    }

    public boolean isTerritoryHostile(Territory territory) {
        return units.stream().anyMatch(unit -> areHostile(territory, unit));
    }

    public boolean areAllFriendlyToTerritory(Territory territory) {
        return units.stream().allMatch(unit -> areFriendly(territory, unit));
    }
}