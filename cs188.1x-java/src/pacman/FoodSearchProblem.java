package pacman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import util.Position;
import util.PositionStandard;
import util.Util;

/**
 * A search problem associated with finding the a path that collects all of the
    food (dots) in a Pacman game.

    A search state in this problem is a tuple ( pacmanPosition, foodGrid ) where
      pacmanPosition: a tuple (x,y) of integers specifying Pacman's position
      foodGrid:       a Grid (see game.py) of either True or False, specifying remaining food
 * @author archie
 */
public class FoodSearchProblem implements SearchProblem<GameStateFoodSearchProblem, GameStateSuccessorFoodSearchProblem> {
    
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

    @Override
    public GameStateFoodSearchProblem getStartState() {
        return startingGameState;
    }

    @Override
    public boolean isGoalState(final GameStateFoodSearchProblem state) {
        return state.isEmpty();
    }

    /** Returns successor states, the actions they require, and a cost of 1. */
    @Override
    public List getSuccessors(final GameStateFoodSearchProblem state) {
        
        final List successors = new ArrayList();
        _expanded += 1;
        for(Direction direction : Arrays.asList(Direction.North, Direction.South, Direction.East, Direction.West)) {
            final Position pos = state.getPacmanPosition();
            final DirectionVector dv = direction.toVector();
            final double x = pos.getX();
            final double y = pos.getY();
            final double dx = dv.getX();
            final double dy = dv.getY();
            final int nextx = (int)(x + dx);
            final int nexty = (int)(y + dy);
            if(!walls.get(nextx, nexty)) {
                final Grid nextFood = state.getFood().copy();
                nextFood.set(nextx, nexty, false);
                successors.add(
                        new GameStateSuccessorFoodSearchProblem(
                            new GameStateFoodSearchProblem(
                                PositionStandard.newInstance(nextx, nexty),
                                nextFood),
                            direction,
                            1));
            }
        }
        return successors;
    }

    /** Returns the cost of a particular sequence of actions.  If those actions
        include an illegal move, return maximum cost */
    @Override
    public int getCostOfActions(final List<Direction> actions) {
        
        final Position pos = getStartState().getPacmanPosition();
        int x = pos.getFloorX();
        int y = pos.getFloorY();
        int cost = 0;
        for(Direction action : actions) {
            // figure out the next state and see whether it's legal
            final DirectionVector vector = action.toVector();
            final double dx = vector.getX();
            final double dy = vector.getY();
            x = (int)(x + dx);
            y = (int)(y + dy);
            if(walls.get(x,y)) {
                return Util.getMaximumCost();
            }
            cost += 1;
        }
        return cost;
    }
    
    @Override
    public int getExpanded() {
        return _expanded;
    }
}
