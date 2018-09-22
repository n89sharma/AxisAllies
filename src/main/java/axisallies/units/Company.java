package axisallies.units;

import axisallies.GameEntity;
import axisallies.board.Territory;
import axisallies.board.TerritoryType;
import axisallies.nations.NationType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static axisallies.board.Board.areFriendly;
import static axisallies.board.Board.areHostile;
import static axisallies.board.TerritoryType.SEA;
import static axisallies.units.UnitType.SUBMARINE;
import static java.util.stream.Collectors.toSet;

public class Company extends GameEntity {

    private Set<Unit> units = new HashSet<>();

    public static Company buildCompany(NationType nationType, UnitType... unitTypes) {
        Company company = new Company();
        Set<Unit> units = Arrays.stream(unitTypes)
            .map(unitType -> Unit.buildUnitOfNation(unitType, nationType))
            .collect(toSet());
        company.setUnits(units);
        return company;
    }

    public void mergeCompanies(Company otherCompany) {
        units.addAll(otherCompany.units);
    }

    public void addUnit(Unit unit) {
        units.add(unit);
    }

    public void removeUnit(Unit unit) {
        units.remove(unit);
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

    public Company extractSubmarines() {

        Company submarineCompany = new Company();
        for (Unit unit : units) {
            if (unit.isType(SUBMARINE)) {
                units.remove(unit);
                submarineCompany.addUnit(unit);
            }
        }
        return submarineCompany;
    }

    public Set<Unit> getUnits() {
        return units;
    }

    public void setUnits(Set<Unit> units) {
        this.units = units;
    }
}