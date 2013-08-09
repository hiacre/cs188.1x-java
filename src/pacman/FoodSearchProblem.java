/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import util.Position;
import util.PositionStandard;

/**
 * A search problem associated with finding the a path that collects all of the
    food (dots) in a Pacman game.

    A search state in this problem is a tuple ( pacmanPosition, foodGrid ) where
      pacmanPosition: a tuple (x,y) of integers specifying Pacman's position
      foodGrid:       a Grid (see game.py) of either True or False, specifying remaining food
 * @author archie
 */
public class FoodSearchProblem {
    
    private final Position startPosition;
    private final Grid startFood;
    private final Grid walls;
    private final GameStateFoodSearchProblem startingGameState;
    private int _expanded;
    private final HashMap heuristicInfo;
    
    public FoodSearchProblem(final GameStateFoodSearchProblem startingGameState) {
        this.startPosition = startingGameState.getPacmanPosition();
        this.startFood = startingGameState.getFood();
        this.walls = startingGameState.getWalls();
        this.startingGameState = startingGameState;
        this._expanded = 0;
        this.heuristicInfo = new HashMap();  // A dictionary for the heuristic to store information
    }

    public GameStateFoodSearchProblem getStartState() {
        return startingGameState;
    }

    public boolean isGoalState(final GameStateFoodSearchProblem state) {
        return state.isEmpty();
    }

    /** Returns successor states, the actions they require, and a cost of 1. */
    public List getSuccessors(final GameStateFoodSearchProblem state) {
        
        final List successors = new ArrayList();
        _expanded += 1;
        for(Direction direction : Arrays.asList(Direction.North, Direction.South, Direction.East, Direction.West)) {
            final Position pos = state.getPacmanPosition();
            final DirectionVector dv = direction.toVector();
            final int x = pos.getX();
            final int y = pos.getY();
            final double dx = dv.getX();
            final double dy = dv.getY();
            final int nextx = (int) Math.floor(x + dx);
            final int nexty = (int) Math.floor(y + dy);
            if(!walls.get(nextx, nexty)) {
                final Grid nextFood = state.getFood().copy();
                nextFood.set(nextx, nexty, false);
                //successors.append( ( ((nextx, nexty), nextFood), direction, 1) );
                successors.add(
                        new GameStateSuccessorFoodSearchProblemStandard(
                            new GameStateFoodSearchProblemStandard(
                                PositionStandard.newInstance(nextx, nexty),
                                nextFood),
                            direction,
                            1));
            }
        }
        return successors;
    }

    /** Returns the cost of a particular sequence of actions.  If those actions
        include an illegal move, return 999999 */
    private int getCostOfActions(final List<Direction> actions) {
        
        final Position pos = getStartState().getPacmanPosition();
        int x = pos.getX();
        int y = pos.getY();
        int cost = 0;
        for(Direction action : actions) {
            // figure out the next state and see whether it's legal
            final DirectionVector vector = action.toVector();
            final double dx = vector.getX();
            final double dy = vector.getY();
            x = (int) Math.floor(x + dx);
            y = (int) Math.floor(y + dy);
            if(walls.get(x,y)) {
                return 999999;
            }
            cost += 1;
        }
        return cost;
    }
}
