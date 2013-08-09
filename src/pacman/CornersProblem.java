/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Position;
import util.PositionStandard;

/**
 * This search problem finds paths through all four corners of a layout.

    You must select a suitable state space and successor function
 * @author archie
 */
public class CornersProblem implements SearchProblem {
    
    private final Logger logger = Logger.getLogger(getClass().getPackage().getName());
    private GameStateCornersProblem startState;
    private int _expanded;

    /** Stores the walls, pacman's starting position and corners. */
    public CornersProblem(final GameStateCornersProblem startingGameState) {

        final Grid walls = startingGameState.getWalls();
        final Position startingPosition = startingGameState.getPacmanPosition();
        final int top = walls.getHeight() - 2;
        final int right = walls.getWidth() - 2;
        final List<? extends Position> corners = Arrays.asList(
                PositionStandard.newInstance(1,1),
                PositionStandard.newInstance(1,top),
                PositionStandard.newInstance(right, 1),
                PositionStandard.newInstance(right, top));
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
        final List cornersState = new ArrayList<>();
        for(Position corner : corners) {
            cornersState.add(startingPosition == corner);
        }
        startState = new GameStateCornersProblemStandard(walls, startingPosition, cornersState);
    }
        

    /** Returns the start state (in your state space, not the full Pacman state space) */
    @Override
    public GameStateCornersProblem getStartState() {
        return startState;
    }

    /** Returns whether this search state is a goal state of the problem */
    public boolean isGoalState(final GameStateCornersProblem gameState) {
        return gameState.getCornersState().equals(Arrays.asList(true, true, true, true));
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
    public List getSuccessors(final GameStateCornersProblem state) {

        final List successors = new ArrayList<>();
        for(Direction action : Arrays.asList(Direction.North, Direction.South, Direction.East, Direction.West)) {
            // Add a successor state to the successor list if the action is legal
            // Here's a code snippet for figuring out whether a new position hits a wall:
            //   x,y = currentPosition
            //   dx, dy = Actions.directionToVector(action)
            //   nextx, nexty = int(x + dx), int(y + dy)
            //   hitsWall = self.walls[nextx][nexty]
            final Position pos = state.getPacmanPosition();
            final int x = pos.getX();
            final int y = pos.getY();
            final DirectionVector v = action.toVector();
            final double dx = v.getX();
            final double dy = v.getY();
            final int nextx = (int) Math.floor(x + dx);
            final int nexty = (int) Math.floor(y + dy);
            final boolean hitsWall = state.getWalls().get(nextx, nexty);
            if(!hitsWall) {
                final List cornersState = new ArrayList();
                for(int i=0; i<4; i++) {
                    cornersState.add(
                            state.getCornersState().get(i)
                            ||
                            (nextx == corners.get(i).getX() && nexty == corners.get(i).getY()));
                }
                //successors.append((((nextx,nexty),cornersState),action,self.getCostOfActions([action])))
                successors.add(
                        new GameStateSuccessorCornersProblemStandard(
                            new GameStateCornersProblemStandard(
                                state.getWalls(),
                                PositionStandard.newInstance(nextx, nexty),
                                cornersState),
                            action,
                            getCostOfAction(action)));
            }
        }

        _expanded += 1;
        return successors;
    }
     

    /** Returns the cost of a particular sequence of actions.  If those actions
        include an illegal move, return 999999.  This is implemented for you. */
    public int getCostOfActions(final List<Direction> actions) {
        if(actions == null) {
            return 999999;
        }
        int x = startingPosition.getX();
        int y = startingPosition.getY();
        for(Direction action : actions) {
            final DirectionVector v = action.toVector();
            final double dx = v.getX();
            final double dy = v.getY();
            x = (int) Math.floor(x + dx);
            y = (int) Math.floor(y + dy);
            if(walls.get(x,y)) {
                return 999999;
            }
        }
        return actions.size();
    }
}
