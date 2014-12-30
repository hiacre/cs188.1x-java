package pacman;

/**
 * An agent that goes West until it can't.
 */
public class GoWestAgent extends AgentAbstract {
    
    public GoWestAgent() {
        super(null);
    }
    
    @Override
    public Direction getAction(final GameState1 state, final Timeout timeout) {
        // TODO make use of Timeout
        if(state.getLegalPacmanActions().contains(Direction.West)) {
            return Direction.West;
        } else {
            return Direction.Stop;
        }
    }
}
