package axisallies;

import axisallies.gameplay.Game;
import java.io.IOException;


public class AxisAllies {
    public static void main (String[] args) throws IOException {
        System.out.println("Hello!");
        Game aGame = new Game();
    }
}

/**
 * 1) Spelling errors in adjacency list
 * 2) Connect geographic zones with a graph that uses their territory name as nodes
 * 3) 
 * 
 * getMap gives a graph of strings. strings are the names of the regions that are connected.
 * boardSetup gives a list of regions. regions contain information about nations, units, etc.
 * Nation has information about the players units, regions, occupied etc.
 * 
 * 
 * Should there be separate objects for every unit? Every unit added with separate object or now.
 * 
 * Territory    - nation type, territory name.
 * Nation       - nation type, list of territory names, list of units.
 * Units        - nation type, unit type, territory name, list of units.
 * 
 * Units belong to nation.
 * Units can contain other units.
 * 
 * 
 * > GERMANY TURN - PHASE 2 COMBAT MOVE: move battleship to 4
 * > GERMANY TURN - PHASE 2 COMBAT MOVE: list units
 * > 
 */
