package axisallies.board;

import axisallies.nations.NationType;

public class Territory {

    private String territoryName;
    private NationType nationType;
    private int IPC;

    public Territory(String territoryName, NationType nationType, int IPC) {
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

}
