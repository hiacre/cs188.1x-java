/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.util.Collection;
import util.Position;

/**
 * These functions govern how pacman interacts with his environment under
    the classic game rules.
 * @author archie
 */
public class PacmanRules {
    
    private final static int PACMAN_SPEED = 1;

    /** Returns a list of possible actions. */
    public static Collection getLegalActions(final GameState state) {
        return Actions.getPossibleActions(
                state.getPacmanState().getConfiguration(),
                state.getData().getLayout().getWalls());
    }
    
    /** Edits the state to reflect the results of the action. */
    public static void applyAction(final GameState state, final Object action) {
        final Collection legal = PacmanRules.getLegalActions( state );
        if(!legal.contains(action)) {
            throw new RuntimeException("Illegal action " + action.toString());
        }

        final Object pacmanState = state.getData().getAgentStates().get(0);

        // Update Configuration
        final Object vector = Actions.directionToVector( action, PACMAN_SPEED );
        pacmanState.setConfiguration(pacmanState.getConfiguration().generateSuccessor( vector ));

        // Eat
        final Position next = pacmanState.getConfiguration().getPosition();
        final Position nearest = nearestPoint( next );
        if(next.manhattanDistance(nearest) <= 0.5) {
            // Remove food
            consume(nearest, state);
        }
    }

    public static void consume(final Position position, final GameState state) {
        final int x = position.getX();
        final int y = position.getY();
        
        // Eat food
        if(state.getData().getFood().get(x).get(y)) {
            state.getData().setScoreChange(state.getData().getScoreChange() + 10);
            state.getData().setFood(state.getData().getFood().copy());
            state.getData().getFood().get(x).set(y, false);
            state.getData().setFoodEaten(position);
            // TODO: cache numFood?
            final int numFood = state.getNumFood();
            if(numFood == 0 && !state.getData().getLose()) {
                state.getData().setScoreChange(state.getData().getScoreChange() + 500);
                state.getData().setWin(true);
            }
        }
        // Eat capsule
        if(state.getCapsules().contains(capsules)) {
            state.getData().getCapsules().remove( position );
            state.getData().setCapsuleEaten(position);
            // Reset all ghosts' scared timers
            for(int i=1; i<state.getData().getAgentStates().size(); i++) {
                state.getData().getAgentStates().get(i).setScaredTimer(SCARED_TIME);
            }
        }
    }
}
