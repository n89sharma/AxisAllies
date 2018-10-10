package axisallies;

import axisallies.gameplay.Game;

import java.io.IOException;
import java.lang.String;
import java.util.UUID;

public class AxisAllies {
    public static void main(String[] args) throws IOException, InterruptedException {
         Game aGame = new Game();
         aGame.run();
    }
}
