package pacman;

import java.util.List;
import util.Position;

/**
 * A SearchAgent for FoodSearchProblem using A* and your foodHeuristic
 * @author archie
 */
public class AStarCornersAgent extends SearchAgent {

    public AStarCornersAgent() {
        super();
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
    private Object cornersHeuristic(final CornersProblem problem) {

        final List<Position> corners = problem.getCorners();   // These are the corner coordinates
        final Grid walls = problem.getWalls();  // These are the walls of the maze, as a Grid

        /*** YOUR CODE HERE ***/
        return 0;   // Default to trivial solution
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
