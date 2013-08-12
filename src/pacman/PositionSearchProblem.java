/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Position;
import util.PositionStandard;

/**
 *     A search problem defines the state space, start state, goal test,
    successor function and cost function.  This search problem can be
    used to find paths to a particular point on the pacman board.

    The state space consists of (x,y) positions in a pacman game.

    Note: this search problem is fully specified; you should NOT change it.
 * @author archie
 */
public class PositionSearchProblem {
    
    private final Grid walls;
    private Object startState;
    private final Position goal;
    private final CostFunction costFn;
    private final Boolean visualize;
    
    private final Logger logger = Logger.getLogger(getClass().getPackage().getName());
    private final Map _visited;
    private final List _visitedList;
    private final int _expanded;

    /** Stores the start and goal.

        gameState: A GameState object (pacman.py)
        costFn: A function from a search state (tuple) to a non-negative number
        goal: A position in the gameState
        */
    public PositionSearchProblem(
            final GameState1 gameState,
            CostFunction costFn,
            Position goal,
            Object start,
            Boolean warn,
            Boolean visualize) {
        
        if(costFn == null) {
            costFn = new CostFunctionAlwaysOne();
        }
        if(goal == null) {
            goal = PositionStandard.newInstance(1,1);
        }
        if(warn == null) {
            warn = true;
        }
        if(visualize == null) {
            visualize = true;
        }
        
        this.walls = gameState.getWalls();
        this.startState = gameState.getPacmanPosition();
        if(start != null) {
            this.startState = start;
        }
        this.goal = goal;
        this.costFn = costFn;
        this.visualize = visualize;
        if(warn && (gameState.getNumFood() != 1 || !gameState.hasFood(goal.getX(), goal.getY()))) {
            logger.log(Level.WARNING, "Warning: this does not look like a regular search maze");
            System.out.println("Warning: this does not look like a regular search maze");
        }

        // For display purposes
        this._visited = new HashMap();
        this._visitedList = new ArrayList();
        this._expanded = 0;
    }

    public Object getStartState() {
        return this.startState;
    }

    public boolean isGoalState(public Object state) {
        final boolean isGoal = (state == goal);

        // For display purposes only
        if(isGoal && visualize) {
            _visitedlist.add(state);
            import __main__;
            // mainDisplay - search for _display in original Python
            // Search elsewhere in this project for mainDisplay
            if 'mainDisplay' in dir(__main__):
                if 'drawExpandedCells' in dir(__main__.mainDisplay): #@UndefinedVariable
                    __main__.mainDisplay.drawExpandedCells(self._visitedlist) #@UndefinedVariable

        return isGoal;
    }

    def getSuccessors(self, state):
        """
        Returns successor states, the actions they require, and a cost of 1.

         As noted in search.py:
             For a given state, this should return a list of triples,
         (successor, action, stepCost), where 'successor' is a
         successor to the current state, 'action' is the action
         required to get there, and 'stepCost' is the incremental
         cost of expanding to that successor
        """

        successors = []
        for action in [Directions.NORTH, Directions.SOUTH, Directions.EAST, Directions.WEST]:
            x,y = state
            dx, dy = Actions.directionToVector(action)
            nextx, nexty = int(x + dx), int(y + dy)
            if not self.walls[nextx][nexty]:
                nextState = (nextx, nexty)
                cost = self.costFn(nextState)
                successors.append( ( nextState, action, cost) )

        # Bookkeeping for display purposes
        self._expanded += 1
        if state not in self._visited:
            self._visited[state] = True
            self._visitedlist.append(state)

        return successors

    def getCostOfActions(self, actions):
        """
        Returns the cost of a particular sequence of actions.  If those actions
        include an illegal move, return maximum cost
        """
        if actions == None: return Util.getMaximumCost()
        x,y= self.getStartState()
        cost = 0
        for action in actions:
            # Check figure out the next state and see whether its' legal
            dx, dy = Actions.directionToVector(action)
            x, y = int(x + dx), int(y + dy)
            if self.walls[x][y]: return Util.getMaximumCost()
            cost += self.costFn((x,y))
        return cost    
}
