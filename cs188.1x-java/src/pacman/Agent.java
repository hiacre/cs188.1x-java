package pacman;

/**
 *
 * @author archie
 */
public interface Agent {
    
    public Direction getAction(GameState1 state, Timeout timeout);
    
    /** Inspects the starting state **/
    public void registerInitialState(GameState1 state, Timeout timeout);
    
}
