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

    public static Company buildCompany(NationType nationType, UnitType ... unitTypes) {
        Company company  = new Company();
        Set<Unit> units = Arrays.stream(unitTypes)
            .map(Unit::buildUnit)
            .collect(toSet());
        units.forEach(u -> u.setNationType(nationType));
        company.setUnits(units);
        return company;
    }

    public void mergeCompanies(Company otherCompany) {
        units.addAll(otherCompany.units);
    }

    public void setUnits(Set<Unit> units) {
        this.units = units;
    }

    public void addUnit(Unit unit) {
        units.add(unit);
    }

    public void removeUnit(Unit unit) {
        units.remove(unit);
    }

    public Set<Unit> getUnits() {
        return this.units;
    }

    public boolean hasType(UnitType unitType) {
        return units.stream()
            .anyMatch(unit -> unit.getUnitType().equals(unitType));
    }

    public boolean areAllOfType(TerritoryType territoryType) {
        return units.stream()
            .allMatch(unit -> unit.getUnitType().getTerritoryType().equals(territoryType));
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

    public boolean containsUnit(Unit unit) {
        return units.contains(unit);
    }
}