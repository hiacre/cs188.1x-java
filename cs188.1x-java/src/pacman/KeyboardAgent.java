package pacman;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import util.Util;

/**
 * An agent controlled by the keyboard.
 * @author archie
 */
public class KeyboardAgent extends AgentAbstract {

    // NOTE: Arrow keys also work.
    private final static char WEST_KEY  = 'a';
    private final static char EAST_KEY  = 'd';
    private final static char NORTH_KEY = 'w';
    private final static char SOUTH_KEY = 's';
    private final static char STOP_KEY = 'q';
    private Direction lastMove;
    private List<Character> keys;

    public KeyboardAgent() {
        this(0);
    }
    public KeyboardAgent(final int index) {
        super(index);
        lastMove = Direction.Stop;
        this.keys = new ArrayList<>();
    }

    @Override
    public Direction getAction(final GameState1 state) {
        final List<Character> keys2 = new ArrayList<>();
        keys2.addAll(GraphicsUtils.keys_waiting());
        keys2.addAll(GraphicsUtils.keys_pressed());
        if(!keys2.isEmpty()) {
            this.keys = keys2;
        }

        final Collection<Direction> legal = state.getLegalActions(getIndex());
        Direction move = getMove(legal);

        if(Direction.Stop.equals(move)) {
            // Try to move in the same direction as before
            if(legal.contains(lastMove)) {
                move = lastMove;
            }
        }

        if(this.keys.contains(STOP_KEY) && legal.contains(Direction.Stop)) {
            move = Direction.Stop;
        }

        if(!legal.contains(move)) {
            move = Util.randomChoice(legal);
        }

        lastMove = move;
        return move;
    }

    private Direction getMove(final Collection<Direction> legal) {
        final Direction move = Direction.Stop;
        // TODO don't think this handles arrow keys properly
        if((keys.contains(WEST_KEY) || keys.contains("Left")) && legal.contains(Direction.West)) {
            return Direction.West;
        }
        if((keys.contains(EAST_KEY) || keys.contains("Right")) && legal.contains(Direction.East)) {
            return Direction.East;
        }
        if((keys.contains(NORTH_KEY) || keys.contains("Up")) && legal.contains(Direction.North)) {
            return Direction.North;
        }
        if((keys.contains(SOUTH_KEY) || keys.contains("Down")) && legal.contains(Direction.South)) {
            return Direction.South;
        }
        return null;
    }
    
    protected List<Character> getKeys() {
        return keys;
    }
}
