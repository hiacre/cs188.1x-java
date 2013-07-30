/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

/**
 *
 * @author archie
 */
public interface SearchProblem {
    
    /** Returns the start state for the search problem */
    public State getStartState();
    
    /** Returns true if and only if the state is a valid goal state */
    public boolean isGoalState(State state);
    
    /** For a given state, this should return a list of triples,
        (successor, action, stepCost), where 'successor' is a
        successor to the current state, 'action' is the action
        required to get there, and 'stepCost' is the incremental
        cost of expanding to that successor */
    public List<Successor> getSuccessors(State state);
    
    /** This method returns the total cost of a particular sequence of actions.  The sequence must
        be composed of legal moves */
    public Cost getCostOfActions(List<Direction> actions);
    
    
}
