package axisallies.gameplay;

import axisallies.GameResponse;
import axisallies.board.Board;
import axisallies.board.BoardBuilder;
import axisallies.nations.Nation;
import axisallies.nations.NationType;
import axisallies.units.UnitType;

import java.io.IOException;
import java.util.Map;

import static axisallies.nations.NationType.GERMANY;

public class Game {

    private Board board;

    public Game() throws IOException {
        this.board = BoardBuilder.build();
    }

    public void run() {
        System.out.println(board.getNations().get(GERMANY));
    }

    private GameResponse orderUnitsForNation(Map<UnitType, Integer> unitOrder, NationType nationType) {

        GameResponse gameResponse = new GameResponse();
        Nation nation = board.getNations().get(nationType);
        int orderCost = unitOrder.entrySet()
                .stream()
                .mapToInt(entry -> entry.getKey().getProductionCost() * entry.getValue())
                .sum();

        if (orderCost <= nation.getIpc()) {
            StringBuilder builder = new StringBuilder("Not enough points in the nation's treasury for the unit order.\n");
            builder.append("Nation : " + nationType.getNationTypeString() + "\n");
            builder.append("Treasury : " + nation.getIpc() + "\n");
            builder.append("Points required : " + orderCost + "\n");
            gameResponse.addError(builder.toString());
        }

        if(!gameResponse.hasErrors()) {
            nation.purchaseUnits(unitOrder);
        }

        return gameResponse;
    }
}