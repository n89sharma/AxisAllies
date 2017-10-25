package axisallies;

import axisallies.board.Map;
import java.io.IOException;

public class AxisAllies {
    public static void main (String[] args) throws IOException {
        System.out.println("Hello!");
        Map.run();
    }
}

/**
 * 1) Spelling errors in adjacency list
 * 2) Connect geographic zones with a graph that uses their region name as nodes
 * 3) 
 * 
 * getMap gives a graph of strings. strings are the names of the regions that are connected.
 * boardSetup gives a list of regions. regions contain information about nations, units, etc.
 * Nation has information about the players units, regions, occupied etc.
 * 
 * 
 * Should there be separate objects for every unit? Every unit added with separate object or now.
 * 
 * Territory    - nation type, region name.
 * Nation       - nation type, list of region names, list of units.
 * Units        - nation type, unit type, region name, list of units.
 * 
 * Units belong to nation.
 * Units can contain other units.
 * 
 * 
 * > GERMANY TURN - PHASE 2 COMBAT MOVE: move battleship to 4
 * > GERMANY TURN - PHASE 2 COMBAT MOVE: list units
 * > 
 */
