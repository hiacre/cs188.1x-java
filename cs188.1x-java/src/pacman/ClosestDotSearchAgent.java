package pacman;

import common.Position;
import java.util.ArrayList;
import java.util.List;

/**
 * Search for all food using a sequence of searches
 * @author archie
 */
public class ClosestDotSearchAgent extends SearchAgent {
    
    private ArrayList<Direction> actions;
    private int actionIndex;
    
    public ClosestDotSearchAgent() {
        super(null, null, null);
    }
            
    @Override
    public void registerInitialState(final GameState1 state) {
        this.actions = new ArrayList<>();
        GameState1 currentState = state;
        while(currentState.getFood().getCount() > 0) {
            final List<Direction> nextPathSegment = this.findPathToClosestDot(currentState);  // The missing piece
            this.actions.addAll(nextPathSegment);
            for(Direction action : nextPathSegment) {
                final List<Direction> legal = currentState.getLegalActions(0);
                if(!legal.contains(action)) {
                    final StringBuilder sb = new StringBuilder();
                    sb.append("findPathToClosestDot returned an illegal move: ");
                    sb.append(action).append("; currentState: ").append(currentState);
                    throw new RuntimeException(sb.toString());
                }
                currentState = currentState.generateSuccessor(0, action);
            }
        }
        this.actionIndex = 0;
        System.out.println("Path found with cost " + this.actions.size());
    }
        
        
    /** Returns a path (a list of actions) to the closest dot, starting from gameState */
    public List<Direction> findPathToClosestDot(final GameState1 gameState) {
        
        // Here are some useful elements of the startState
        final Position startPosition = gameState.getPacmanPosition();
        final Grid food = gameState.getFood();
        final Grid walls = gameState.getWalls();
        final AnyFoodSearchProblem problem = new AnyFoodSearchProblem(gameState);

        /*** YOUR CODE HERE ***/
        throw new UnsupportedOperationException();
    }
}
