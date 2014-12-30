package pacman;

import util.Counter;

/**
 * A ghost that chooses a legal action uniformly at random.
 */
public class RandomGhost extends GhostAgent {
    
    public RandomGhost(final int index) {
        super(index);
    }
    
    public Object getDistribution(final GameState1 state) {
        final Counter<Direction> dist = new Counter<>();
        for(Direction a : state.getLegalActions(this.getIndex())) {
            dist.put(a, 1);
        }
        return dist.getNormalized();
    }
}
