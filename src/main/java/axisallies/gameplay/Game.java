package axisallies.gameplay;

import axisallies.board.Board;
import axisallies.units.Unit;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static axisallies.nations.NationType.GERMANY;
import static axisallies.nations.NationType.USA;
import static axisallies.units.land.Infantry.getInfantryCompany;
import static axisallies.units.land.Infantry.infantry;
import static axisallies.units.land.Tank.getTankCompany;
import static axisallies.units.land.Tank.tank;

public class Game {

    private Board board;
    private GeneralCombatConductor generalCombatConductor;

    public Game() throws IOException {
//        this.board = BoardBuilder.sourceBuild();
    }

    public void run() {
        var attack = new HashSet<Unit>();
        attack.addAll(getTankCompany(5, USA));
        attack.addAll(getInfantryCompany(5, USA));
        var defend = getTankCompany(10, GERMANY);
        var gc = new GeneralCombatConductor(attack, defend);

        gc.conductGeneralCombat();
    }

}