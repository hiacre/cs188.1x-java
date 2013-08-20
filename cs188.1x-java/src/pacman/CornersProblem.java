package pacman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Position;
import util.PositionStandard;
import util.Util;

/**
 * This search problem finds paths through all four corners of a layout.

    You must select a suitable state space and successor function
 * @author archie
 */
public class CornersProblem implements SearchProblem<GameStateCornersProblem, GameStateSuccessorCornersProblem> {
    
    private final Logger logger = Logger.getLogger(getClass().getPackage().getName());
    private GameStateCornersProblem startState;
    private int _expanded;
    private final List<Position> corners;
    private final Position startingPosition;
    private final Grid walls;

    /** Stores the walls, pacman's starting position and corners. */
    public CornersProblem(final GameStateCornersProblem startingGameState) {

        walls = startingGameState.getWalls();
        startingPosition = startingGameState.getPacmanPosition();
        final int top = walls.getHeight() - 2;
        final int right = walls.getWidth() - 2;
        corners = new ArrayList<>();
        corners.add(PositionStandard.newInstance(1,1));
        corners.add(PositionStandard.newInstance(1,top));
        corners.add(PositionStandard.newInstance(right, 1));
        corners.add(PositionStandard.newInstance(right, top));
        for(Position corner : corners) {
            if(!startingGameState.hasFood(corner)) {
                logger.log(Level.WARNING, "Warning: no food in corner {0}", corner.toString());
            }
        }
        _expanded = 0; // Number of search nodes expanded
        /**
         * Please add any code here which you would like to use
         * in initializing the problem
         */
        /*** YOUR CODE HERE ***/
    }
        

    /** Returns the start state (in your state space, not the full Pacman state space) */
    @Override
    public GameStateCornersProblem getStartState() {
        /*** YOUR CODE HERE ***/
        throw new UnsupportedOperationException();
    }

    /** Returns whether this search state is a goal state of the problem */
    @Override
    public boolean isGoalState(final GameStateCornersProblem gameState) {
        /*** YOUR CODE HERE ***/
        throw new UnsupportedOperationException();
    }
    
    /**
     * Returns successor states, the actions they require, and a cost of 1.

         As noted in search.py:
             For a given state, this should return a list of triples,
         (successor, action, stepCost), where 'successor' is a
         successor to the current state, 'action' is the action
         required to get there, and 'stepCost' is the incremental
         cost of expanding to that successor
     */
    @Override
    public List<GameStateSuccessorCornersProblem> getSuccessors(final GameStateCornersProblem state) {

        final List<GameStateSuccessorCornersProblem> successors = new ArrayList<>();
        for(Direction action : Arrays.asList(Direction.North, Direction.South, Direction.East, Direction.West)) {
            // Add a successor state to the successor list if the action is legal
            // Here's a code snippet for figuring out whether a new position hits a wall:
            //   x,y = currentPosition
            //   dx, dy = Actions.directionToVector(action)
            //   nextx, nexty = int(x + dx), int(y + dy)
            //   hitsWall = self.walls[nextx][nexty]
            /*** YOUR CODE HERE ***/
        }

        _expanded += 1;
        return successors;
    }
     

    /** Returns the cost of a particular sequence of actions.  If those actions
        include an illegal move, return maximum cost.  This is implemented for you. */
    @Override
    public int getCostOfActions(final List<Direction> actions) {
        if(actions == null) {
            return Util.getMaximumCost();
        }
        int x = (int)startingPosition.getX();
        int y = (int)startingPosition.getY();
        for(Direction action : actions) {
            final DirectionVector v = action.toVector();
            final double dx = v.getX();
            final double dy = v.getY();
            x = (int)(x + dx);
            y = (int)(y + dy);
            if(walls.get(x,y)) {
                return Util.getMaximumCost();
            }
        }
        return actions.size();
    }

    public List<Position> getCorners() {
        return this.corners;
    }

    public Grid getWalls() {
        return this.walls;
    }

    @Override
    public int getExpanded() {
        return _expanded;
    }
}
