package axisallies.board;

import axisallies.nations.NationType;

public class Territory {

    private String territoryName;
    private NationType nationType;
    private int IPC;
    private TerritoryType territoryType;

    public Territory(String territoryName, TerritoryType territoryType, NationType nationType, int IPC) {
        this.territoryName = territoryName;
        this.nationType = nationType;
        this.IPC = IPC;
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

    public enum TerritoryType {
        SEA, LAND, NEUTRAL;
    }

}
