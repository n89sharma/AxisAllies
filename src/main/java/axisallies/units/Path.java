package axisallies.units;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import axisallies.board.Territory;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
public class Path {

    private List<Territory> territories = new ArrayList<>();

    public static Path createPath(Map<String, Territory> territories, String ... territoryNames) {
        Path path = new Path();
        for(String territoryName : territoryNames) {
            path.add(territories.get(territoryName));
        }
        return path;
    }
    
    public Path(List<Territory> territories) {
        this.territories = territories;
    }

    public void add(Territory territory) {
        territories.add(territory);
    }

    public Territory getStart() {
        return territories.get(0);
    }

    public Territory getDestination() {
        return territories.get(territories.size()-1);
    }

    public Path getAllBeforeDestination() {
        return new Path(territories.subList(0, territories.size()-1));
    }

    public List<Territory> getTerritories() {
        return territories;
    }

    public int size() {
        return territories.size();
    }
}