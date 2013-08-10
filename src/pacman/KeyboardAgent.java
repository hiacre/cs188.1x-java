/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.util.ArrayList;
import java.util.List;

/**
 * An agent controlled by the keyboard.
 * @author archie
 */
public class KeyboardAgent extends Agent {

    // NOTE: Arrow keys also work.
    private final static char WEST_KEY  = 'a';
    private final static char EAST_KEY  = 'd';
    private final static char NORTH_KEY = 'w';
    private final static char SOUTH_KEY = 's';
    private final static char STOP_KEY = 'q';
    private final Direction lastMove;
    private final int index;

    public KeyboardAgent() {
        KeyboardAgent(0);
    }
    public KeyboardAgent(final int index) {

        lastMove = Direction.Stop;
        this.index = index;
        List keys = new ArrayList<>();
    }

    def getAction( self, state):
        from graphicsUtils import keys_waiting
        from graphicsUtils import keys_pressed
        keys = keys_waiting() + keys_pressed()
        if keys != []:
            self.keys = keys

        legal = state.getLegalActions(self.index)
        move = self.getMove(legal)

        if move == Directions.STOP:
            # Try to move in the same direction as before
            if self.lastMove in legal:
                move = self.lastMove

        if (self.STOP_KEY in self.keys) and Directions.STOP in legal: move = Directions.STOP

        if move not in legal:
            move = random.choice(legal)

        self.lastMove = move
        return move

    def getMove(self, legal):
        move = Directions.STOP
        if   (self.WEST_KEY in self.keys or 'Left' in self.keys) and Directions.WEST in legal:  move = Directions.WEST
        if   (self.EAST_KEY in self.keys or 'Right' in self.keys) and Directions.EAST in legal: move = Directions.EAST
        if   (self.NORTH_KEY in self.keys or 'Up' in self.keys) and Directions.NORTH in legal:   move = Directions.NORTH
        if   (self.SOUTH_KEY in self.keys or 'Down' in self.keys) and Directions.SOUTH in legal: move = Directions.SOUTH
        return move
}
