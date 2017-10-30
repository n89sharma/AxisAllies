package axisallies.board;

import axisallies.nations.NationType;
import axisallies.nations.NationType.TeamType;

public class Territory {

    private String territoryName;
    private NationType nationType;
    private int IPC;
    private TerritoryType territoryType;
    private TeamType teamType;

    public Territory(String territoryName, TerritoryType territoryType, NationType nationType, int IPC) {
        this.territoryName = territoryName;
        this.nationType = nationType;
        this.IPC = IPC;
        this.teamType = this.nationType.getTeamType();
    }

    public String getTerritoryName(){
        return this.territoryName;
    }

    public NationType getNationType() {
        return this.nationType;
    }

    public int getIpc() {
        return IPC;
    }

    public TerritoryType getTerritoryType() {
        return this.territoryType;
    }

    public TeamType getTeamType() {
        return this.teamType;
    }

    public enum TerritoryType {
        SEA, LAND, NEUTRAL;
    }

}
