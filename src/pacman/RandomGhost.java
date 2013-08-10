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
    
    public Object getDistribution(final GameState1 state) {
        Counter<String> dist = CounterStandard.newInstance();
        for a in state.getLegalActions( self.index ): dist[a] = 1.0
        dist.normalize()
        return dist
}
