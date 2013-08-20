package pacman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Position;
import util.PositionStandard;
import util.Util;

/**
 *     A search problem defines the state space, start state, goal test,
    successor function and cost function.  This search problem can be
    used to find paths to a particular point on the pacman board.

    The state space consists of (x,y) positions in a pacman game.

    Note: this search problem is fully specified; you should NOT change it.
 * @author archie
 */
public class PositionSearchProblem implements SearchProblem<GameStatePositionSearchProblem, GameStateSuccessorPositionSearchProblem> {
    
    private final Grid walls;
    private GameStatePositionSearchProblem startState;
    private final int goalX;
    private final int goalY;
    private final CostFunction costFn;
    private final Boolean visualize;
    
    private final Logger logger = Logger.getLogger(getClass().getPackage().getName());
    private final Map _visited;
    private final List _visitedList;
    private int _expanded;

    /** Stores the start and goal.

        gameState: A GameState object (pacman.py)
        costFn: A function from a search state (tuple) to a non-negative number
        goal: A position in the gameState
        */
    public PositionSearchProblem(
            final GameState1 gameState,
            CostFunction costFn,
            Integer goalX,
            Integer goalY,
            GameStatePositionSearchProblem start,
            Boolean warn,
            Boolean visualize) {
        
        if(costFn == null) {
            costFn = new CostFunctionAlwaysOne();
        }
        goalX = goalX == null ? 1 : goalX;
        goalY = goalY == null ? 1 : goalY;
        if(warn == null) {
            warn = true;
        }
        if(visualize == null) {
            visualize = true;
        }
        
        this.walls = gameState.getWalls();
        this.startState = new GameStatePositionSearchProblem(gameState.getPacmanPosition());
        if(start != null) {
            this.startState = start;
        }
        this.goalX = goalX;
        this.goalY = goalY;
        this.costFn = costFn;
        this.visualize = visualize;
        if(warn && (gameState.getNumFood() != 1 || !gameState.hasFood(goalX, goalY))) {
            logger.log(Level.WARNING, "Warning: this does not look like a regular search maze");
            System.out.println("Warning: this does not look like a regular search maze");
        }

        // For display purposes
        this._visited = new HashMap();
        this._visitedList = new ArrayList();
        this._expanded = 0;
    }

    @Override
    public GameStatePositionSearchProblem getStartState() {
        return this.startState;
    }

    @Override
    public boolean isGoalState(final GameStatePositionSearchProblem state) {
        final Position pos = state.getPacmanPosition();
        final boolean isGoal = (pos.getX() == goalX && pos.getY() == goalY);

        // For display purposes only
        if(isGoal && visualize) {
            _visitedList.add(state);
            // import __main__;
            // TODO mainDisplay - search for _display in original Python
            // Search elsewhere in this project for mainDisplay
            //if 'mainDisplay' in dir(__main__):
            //    if 'drawExpandedCells' in dir(__main__.mainDisplay): #@UndefinedVariable
            //        __main__.mainDisplay.drawExpandedCells(self._visitedlist) #@UndefinedVariable
        }
        return isGoal;
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
    public List<GameStateSuccessorPositionSearchProblem> getSuccessors(final GameStatePositionSearchProblem state) {
        final List<GameStateSuccessorPositionSearchProblem> successors = new ArrayList();
        for(Direction action : Arrays.asList(Direction.North, Direction.South, Direction.East, Direction.West)) {
            final double x = state.getPacmanPosition().getX();
            final double y = state.getPacmanPosition().getY();
            final DirectionVector vector = action.toVector();
            final double dx = vector.getX();
            final double dy = vector.getY();
            final int nextx = (int)(x+dx);
            final int nexty = (int)(y+dy);
            if(!this.walls.get(nextx,nexty)) {
                final Position nextState = PositionStandard.newInstance(nextx, nexty);
                final int cost = this.costFn.eval(nextState);
                successors.add(
                        new GameStateSuccessorPositionSearchProblem(nextState, action, cost));
            }
        }

        // Bookkeeping for display purposes
        this._expanded += 1;
        if(_visited.containsKey(state)) {
            this._visited.put(state, true);
            this._visitedList.add(state);
        }

        return successors;
    }

    /** Returns the cost of a particular sequence of actions.  If those actions
        include an illegal move, return maximum cost */
    @Override
    public int getCostOfActions(final List<Direction> actions) {

        if(actions == null) {
            return Util.getMaximumCost();
        }
        int x = (int)this.getStartState().getPacmanPosition().getX();
        int y = (int)this.getStartState().getPacmanPosition().getY();
        
        int cost = 0;
        for(Direction action : actions) {
            // Check figure out the next state and see whether it's legal
            final DirectionVector vector = action.toVector();
            final double dx = vector.getX();
            final double dy = vector.getY();
            x = (int)(x+dx);
            y = (int)(y+dy);
            if(this.walls.get(x,y)) {
                return Util.getMaximumCost();
            }
            cost += this.costFn.eval(PositionStandard.newInstance(x,y));
        }
        return cost;
    }

    public int getGoalX() {
        return goalX;
    }
    
    public int getGoalY() {
        return goalY;
    }

    @Override
    public int getExpanded() {
        return _expanded;
    }
}
