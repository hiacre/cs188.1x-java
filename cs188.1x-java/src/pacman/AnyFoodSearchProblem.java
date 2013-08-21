package pacman;

import util.Position;

/**
 * A search problem for finding a path to any food.

      This search problem is just like the PositionSearchProblem, but
      has a different goal test, which you need to fill in below.  The
      state space and successor function do not need to be changed.

      This class, AnyFoodSearchProblem,  inherits the methods of the
      PositionSearchProblem class.

      You can use this search problem to help you fill in
      the findPathToClosestDot method.
 * @author archie
 */
public class AnyFoodSearchProblem extends PositionSearchProblem {
    
    private final Grid food;

    /** Stores information from the gameState.  You don't need to change this. */
    public AnyFoodSearchProblem(final GameState1 gameState) {
        super(
            gameState,
            new CostFunctionAlwaysOne(),
            null,
            null,
            new GameStatePositionSearchProblem(gameState.getPacmanPosition()),
            null,
            null);
        // Store the food for later reference
        this.food = gameState.getFood();
    }

    /**
     * The state is Pacman's position. Fill this in with a goal test
       that will complete the problem definition.
     */ 
    public boolean isGoalState(final GameStateAnyFoodSearchProblem state) {
        final Position pos = state.getPacmanPosition();
        final double x = pos.getX();
        final double y = pos.getY();

        /*** YOUR CODE HERE ***/
        throw new UnsupportedOperationException();
    }
}
