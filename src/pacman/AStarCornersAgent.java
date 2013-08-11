/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

/**
 *
 * @author archie
 */
public class AStarCornersAgent {
class AStarCornersAgent(SearchAgent):
    "A SearchAgent for FoodSearchProblem using A* and your foodHeuristic"
    def __init__(self):
        self.searchFunction = lambda prob: search.aStarSearch(prob, cornersHeuristic)
        self.searchType = CornersProblem


def cornersHeuristic(state, problem):
    """
    A heuristic for the CornersProblem that you defined.

      state:   The current search state
               (a data structure you chose in your search problem)

      problem: The CornersProblem instance for this layout.

    This function should always return a number that is a lower bound
    on the shortest path from the state to a goal of the problem; i.e.
    it should be admissible (as well as consistent).
    """
    corners = problem.corners # These are the corner coordinates
    walls = problem.walls # These are the walls of the maze, as a Grid (game.py)

    "*** YOUR CODE HERE ***"
    #return 0 # Default to trivial solution
    position = state[0]
    cornersVisitFlags = state[1]
    
    if cornersVisitFlags == (True, True, True, True):
        return 0
    
    cornersUnvisited = map(lambda x: x[0],
        filter(lambda x: not x[1],
               zip(corners, cornersVisitFlags)))
    
    perms = itertools.permutations(cornersUnvisited)
    
    minCost = 10**100
    for perm in perms:
        cost = travelDistance(position, perm)
        if cost < minCost:
            minCost = cost
    
    return minCost

            
            
def travelDistance(position, corners):
    totalCost = 0
    for corner in corners:
        totalCost += manhattanDistance(position, corner)
        position = corner
    return totalCost

}
