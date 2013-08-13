/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import util.Position;
import util.Util;

/**
 * A SearchAgent for FoodSearchProblem using A* and your foodHeuristic
 * @author archie
 */
public class AStarCornersAgent extends SearchAgent {

    public AStarCornersAgent() {
        this.searchFunction = lambda prob: search.aStarSearch(prob, cornersHeuristic);
        this.searchType = CornersProblem;
    }


    /**
     * A heuristic for the CornersProblem that you defined.

          state:   The current search state
                   (a data structure you chose in your search problem)

          problem: The CornersProblem instance for this layout.

        This function should always return a number that is a lower bound
        on the shortest path from the state to a goal of the problem; i.e.
        it should be admissible (as well as consistent).
     * @param problem
     * @return 
     */
    private Object cornersHeuristic(final Object problem) {

        final List<Position> corners = problem.getCorners();   // These are the corner coordinates
        final Grid walls = problem.getWalls();  // These are the walls of the maze, as a Grid

        /*** YOUR CODE HERE ***/
        //return 0;   // Default to trivial solution
        final Position position = state.get(0);
        final List<Boolean> cornersVisitFlags = state.get(1);

        if(cornersVisitFlags.equals(Arrays.asList(true, true, true, true))) {
            return 0;
        }

        final List<Position> cornersUnvisited = new ArrayList<>();
        for(int i=0; i<corners.size(); i++) {
            if(cornersVisitFlags.get(i)) {
               continue; 
            }
            cornersUnvisited.add(corners.get(i));
        }

        final Collection<List<Position>> perms = Util.getPermutations(cornersUnvisited);

        double minCost = Math.pow(10, 100);
        for(List<Position> perm : perms) {
            final int cost = travelDistance(position, perm);
            if(cost < minCost) {
                minCost = cost;
            }
        }

        return minCost;
    }

            
            
    private int travelDistance(Position position, List<Position> corners) {
        int totalCost = 0;
        for(Position corner : corners) {
            totalCost += position.manhattanDistance(corner);
            position = corner;
        }
        return totalCost;
    }

}
