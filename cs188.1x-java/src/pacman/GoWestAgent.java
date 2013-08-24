package pacman;

/**
 * An agent that goes West until it can't.
 * @author archie
 */
public class GoWestAgent extends AgentAbstract {
    
    public GoWestAgent() {
        super(null);
    }
    
    @Override
    public Direction getAction(final GameState1 state) {
        if(state.getLegalPacmanActions().contains(Direction.West)) {
            return Direction.West;
        } else {
            return Direction.Stop;
        }
    }
}
