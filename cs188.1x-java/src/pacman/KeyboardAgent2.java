package pacman;

import java.util.List;

/**
 * A second agent controlled by the keyboard.
 * @author archie
 */
public class KeyboardAgent2 extends KeyboardAgent {

    // NOTE: Arrow keys also work.
    private final static char WEST_KEY  = 'j';
    private final static char EAST_KEY  = 'l';
    private final static char NORTH_KEY = 'i';
    private final static char SOUTH_KEY = 'k';
    // TODO what's the point in this STOP_KEY being defined
    //      if it's not referenced?
    private final static char STOP_KEY = 'u';

    
    public Direction getMove(final List<Direction> legal) {
        if(getKeys().contains(WEST_KEY) && legal.contains(Direction.West)) {
            return Direction.West;
        }
        if(getKeys().contains(EAST_KEY) && legal.contains(Direction.East)) {
            return Direction.East;
        }
        if(getKeys().contains(NORTH_KEY) && legal.contains(Direction.North)) {
            return Direction.North;
        }
        if(getKeys().contains(SOUTH_KEY) && legal.contains(Direction.South)) {
            return Direction.South;
        }
        return Direction.Stop;
    }
}
