package pacman;

import common.Position;
import java.util.Collection;
import java.util.List;

/**
 * These functions govern how pacman interacts with his environment under
    the classic game rules.
 * @author archie
 */
public class PacmanRules {
    
    public static int SCARED_TIME = 40;
    private final static int PACMAN_SPEED = 1;

    /** Returns a list of possible actions. */
    public static List<Direction> getLegalActions(final GameState1 state) {
        return Actions.getPossibleActions(
                state.getPacmanState().getConfiguration(),
                state.getData().getLayout().getWalls());
    }
    
    /** Edits the state to reflect the results of the action. */
    public static void applyAction(final GameState1 state, final Direction action) {
        final Collection legal = PacmanRules.getLegalActions( state );
        if(!legal.contains(action)) {
            throw new RuntimeException("Illegal action " + action.toString());
        }

        final AgentState pacmanState = state.getData().getAgentStates().get(0);

        // Update Configuration
        final DirectionVector vector = action.toVector(PACMAN_SPEED);
        pacmanState.setConfiguration(pacmanState.getConfiguration().generateSuccessor( vector ));

        // Eat
        final Position next = pacmanState.getConfiguration().getPosition();
        final Position nearest = Position.nearestPoint( next.getX(), next.getY() );
        if(next.manhattanDistance(nearest) <= 0.5) {
            // Remove food
            consume((int)nearest.getX(), (int)nearest.getY(), state);
        }
    }

    public static void consume(final int x, final int y, final GameState1 state) {
        // Eat food
        if(state.getData().getFood().get(x,y)) {
            state.getData().setScoreChange(state.getData().getScoreChange() + 10);
            state.getData().setFood(state.getData().getFood().copy());
            state.getData().getFood().set(x,y,false);
            state.getData().setFoodEaten(x,y);
            // TODO: cache numFood?
            final int numFood = state.getNumFood();
            if(numFood == 0 && !state.getData().getLose()) {
                state.getData().setScoreChange(state.getData().getScoreChange() + 500);
                state.getData().setWin(true);
            }
        }
        // Eat capsule
        if(state.getCapsules().isCapsule(x,y)) {
            state.getData().getCapsules().removeCapsule(x,y);
            state.getData().setCapsuleEaten(x,y);
            // Reset all ghosts' scared timers
            for(int i=1; i<state.getData().getAgentStates().size(); i++) {
                ((GhostState)state.getData().getAgentStates().get(i)).setScaredTimer(SCARED_TIME);
            }
        }
    }
    
    /** Number of moves for which ghosts are scared */
    public static int getScaredTime() {
        return SCARED_TIME;
    }
}
