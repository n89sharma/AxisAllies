package axisallies.board;

import axisallies.GameEntity;

import java.util.ArrayList;
import java.util.List;

public class Path extends GameEntity {

    private List<Territory> territories = new ArrayList<>();

    public Path() {
    }

    public Path(List<Territory> territories) {
        this.territories = territories;
    }

    public static Path createPath(Board board, String... territoryNames) {
        Path path = new Path();
        for (String territoryName : territoryNames) {
            path.add(board.get(territoryName));
        }
        return path;
    }

    public void add(Territory territory) {
        territories.add(territory);
    }

    public Territory getStart() {
        return territories.get(0);
    }

    public Territory getDestination() {
        return territories.get(territories.size() - 1);
    }

    public Path getAllBeforeDestination() {
        return new Path(territories.subList(0, territories.size() - 1));
    }

    public List<Territory> getTerritories() {
        return territories;
    }

    public int size() {
        return territories.size();
    }
}