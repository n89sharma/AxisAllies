package axisallies.units;

import axisallies.GameEntity;
import axisallies.board.Territory;

import java.util.HashSet;
import java.util.Set;

import static axisallies.board.Board.areFriendly;
import static axisallies.board.Board.areHostile;

public class Company extends GameEntity {

    private Set<Unit> units = new HashSet<>();

    public void mergeCompanies(Company otherCompany) {
        units.addAll(otherCompany.units);
    }

    public void addUnit(Unit unit) {
        units.add(unit);
    }

    public void removeUnit(Unit unit) {
        units.remove(unit);
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

    public Set<Unit> getUnits() {
        return units;
    }

    public void setUnits(Set<Unit> units) {
        this.units = units;
    }
}