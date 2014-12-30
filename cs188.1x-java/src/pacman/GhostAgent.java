package pacman;

import util.Counter;
import util.Util;

public abstract class GhostAgent extends AgentAbstract {
    
    public GhostAgent(final int index) {
        super(index);
    }

    @Override
    public Direction getAction(final GameState1 state, final Timeout timeout) {
        // TODO make use of timeout
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
}
