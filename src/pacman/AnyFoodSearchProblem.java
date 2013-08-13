package pacman;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import util.Position;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * A search problem for finding a path to any food.

      This search problem is just like the PositionSearchProblem, but
      has a different goal test, which you need to fill in below.  The
      state space and successor function do not need to be changed.

      The class definition above, AnyFoodSearchProblem(PositionSearchProblem),
      inherits the methods of the PositionSearchProblem.

      You can use this search problem to help you fill in
      the findPathToClosestDot method.
 * @author archie
 */
public class AnyFoodSearchProblem extends PositionSearchProblem {
    
    private final Grid food;

    /** Stores information from the gameState.  You don't need to change this. */
    public AnyFoodSearchProblem(final GameState1 gameState) {
        super(gameState, new CostFunctionAlwaysOne(), null, gameState.getPacmanPosition(), null, null);
        // Store the food for later reference
        this.food = gameState.getFood();
    }

    /**
     * The state is Pacman's position. Fill this in with a goal test
       that will complete the problem definition.
     */ 
    public boolean isGoalState(final GameState1 state) {
        final Position pos = state.getPacmanPosition();
        final int x = pos.getX();
        final int y = pos.getY();

        /**
         * YOUR CODE HERE
         */
        throw new NotImplementedException();
    }
}
