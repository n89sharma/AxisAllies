package axisallies.board;

import axisallies.nations.Nation;
import axisallies.nations.NationType;
import axisallies.players.Player;
import axisallies.units.Unit;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class Board {

    private Map<String, Territory> territories;
    private Map<NationType, Nation> nations;
    private Map<NationType, Player> players;

    public Territory get(String territoryName) {
        return territories.get(territoryName);
    }

    public Set<String> getTerritoryNames() {
        return territories.keySet();
    }

    public static boolean areHostile(Unit unitA, Unit unitB) {
        return unitA.getNationType().getTeamType().isHostileTo(unitB.getNationType().getTeamType());
    }

    public static boolean areHostile(Territory territory, Unit unit) {
        return territory.getNationType().getTeamType().isHostileTo(unit.getNationType().getTeamType());
    };

    public static boolean areFriendly(Territory territory, Unit unit) {
        return !areHostile(territory, unit);
    }

    public static boolean areFriendly(Unit unitA, Unit unitB) {
        return !areHostile(unitA, unitB);
    }
}