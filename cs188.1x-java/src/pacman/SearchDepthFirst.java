/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.util.List;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Search the deepest nodes in the search tree first

    Your search algorithm needs to return a list of actions that reaches
    the goal.  Make sure to implement a graph search algorithm

    To get started, you might want to try some of these simple commands to
    understand the search problem that is being passed in:

    print "Start:", problem.getStartState()
    print "Is the start a goal?", problem.isGoalState(problem.getStartState())
    print "Start's successors:", problem.getSuccessors(problem.getStartState())
    
 * @author archie
 */
public class SearchDepthFirst implements Search {

    @Override
    public List<Direction> getActions(final SearchProblem problem) {
        throw new NotImplementedException();
    }
}
