package axisallies;

import axisallies.gameplay.Game;

import java.io.IOException;
import java.util.concurrent.Callable;


public class AxisAllies implements Callable<Void> {
    public static void main(String[] args)  {

    }

    @Override
    public Void call() throws Exception {
        Game aGame = new Game();
        aGame.run();
        return null;
    }
}
