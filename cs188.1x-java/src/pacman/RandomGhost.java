/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import util.Counter;
import util.CounterStandard;

/**
 * A ghost that chooses a legal action uniformly at random.
 * @author archie
 */
public class RandomGhost extends GhostAgent {
    
    public RandomGhost(final int index) {
        super(index);
    }
    
    public Object getDistribution(final GameState1 state) {
        final Counter<Direction> dist = CounterStandard.newInstance();
        for(Direction a : state.getLegalActions(this.getIndex())) {
            dist.put(a, 1);
        }
        return dist.getNormalized();
    }
}
