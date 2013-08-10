/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

/**
 * A second agent controlled by the keyboard.
 * @author archie
 */
public class KeyboardAgent2 extends KeyboardAgent {

    // NOTE: Arrow keys also work.
    private final static char WEST_KEY  = 'j';
    private final static char EAST_KEY  = 'l'
    private final static char NORTH_KEY = 'i';
    private final static char SOUTH_KEY = 'k';
    private final static char STOP_KEY = 'u';

    public Direction getMove(final Object legal) {
        final Direction move = Direction.Stop;
        if   (self.WEST_KEY in self.keys) and Directions.WEST in legal:  move = Directions.WEST;
        if   (self.EAST_KEY in self.keys) and Directions.EAST in legal: move = Directions.EAST;
        if   (self.NORTH_KEY in self.keys) and Directions.NORTH in legal:   move = Directions.NORTH;
        if   (self.SOUTH_KEY in self.keys) and Directions.SOUTH in legal: move = Directions.SOUTH;
        return move;
    }

}
