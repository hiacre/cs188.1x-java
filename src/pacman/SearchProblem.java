/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.util.List;

/**
 *
 * @author archie
 */
public interface SearchProblem<GS extends IGameState, GSS extends GameStateSuccessor> {
    
    /** Returns the start state for the search problem */
    public GS getStartState();
    
    /** Returns true if and only if the state is a valid goal state */
    public boolean isGoalState(GS state);
    
    /** For a given state, this should return a list of triples,
        (successor, action, stepCost), where 'successor' is a
        successor to the current state, 'action' is the action
        required to get there, and 'stepCost' is the incremental
        cost of expanding to that successor */
    public List<GSS> getSuccessors(GS state);
    
    /** This method returns the total cost of a particular sequence of actions.  The sequence must
        be composed of legal moves */
    public int getCostOfActions(List<Direction> actions);
    
    
}
