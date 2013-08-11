/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import util.Counter;
import util.Util;

/**
 *
 * @author archie
 */
public abstract class GhostAgent implements Agent {
    
    private final int index;
    
    public GhostAgent(final int index) {
        this.index = index;
    }

    @Override
    public Direction getAction(final GameState1 state) {
        final Counter<Direction> dist = getDistribution(state);
        if(dist.isEmpty()) {
            return Direction.Stop;
        } else {
            return Util.chooseFromDistribution( dist );
        }
    }

    /** Returns a Counter encoding a distribution over actions from the provided state. */
    public Counter<Direction> getDistribution(final Object state) {
        throw new UnsupportedOperationException();
    }
    
    public int getIndex() {
        return index;
    }
}
