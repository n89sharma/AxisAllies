package axisallies.players;

import axisallies.GameEntity;
import axisallies.nations.NationType;

public class Player extends GameEntity{
    private String playerName;
    private NationType nationType;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public NationType getNationType() {
        return nationType;
    }

    public void setNationType(NationType nationType) {
        this.nationType = nationType;
    }
}
