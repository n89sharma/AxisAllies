package axisallies.board;

import axisallies.nations.Nation;
import axisallies.nations.NationType;
import axisallies.players.Player;
import com.google.common.graph.MutableGraph;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Board {

    private Map<String, Territory> territories;
    private Map<NationType, Nation> nations;
    private Map<NationType, Player> players;
    private MutableGraph<Territory> graph;
}