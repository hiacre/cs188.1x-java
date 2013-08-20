package pacman;

import util.Position;

/**
 *
 * @author archie
 */
public class FoodHeuristic implements Heuristic {

    public FoodHeuristic() {
    }

    @Override
    public int calculate(GameState1 state, SearchProblem problem) {
        /*
        Your heuristic for the FoodSearchProblem goes here.

        This heuristic must be consistent to ensure correctness.  First, try to come up
        with an admissible heuristic; almost all admissible heuristics will be consistent
        as well.

        If using A* ever finds a solution that is worse uniform cost search finds,
        your heuristic is *not* consistent, and probably not admissible!  On the other hand,
        inadmissible or inconsistent heuristics may find optimal solutions, so be careful.

        The state is a GameStateFoodSearchProblem, where state.getFood() is a
        Grid of either true or false. You can call foodGrid.asList()
        to get a list of food coordinates instead.

        If you want access to info like walls, capsules, etc., you can query the problem.
        For example, problem.walls gives you a Grid of where the walls are.

        If you want to *store* information to be reused in other calls to the heuristic,
        there is a dictionary called problem.heuristicInfo that you can use. For example,
        if you only want to count the walls once and store that value, try:
          problem.heuristicInfo['wallCount'] = problem.walls.count()
        Subsequent calls to this heuristic can access problem.heuristicInfo['wallCount']
        */

        final Position position = state.getPacmanPosition();
        final Grid foodGrid = state.getFood();
        /*** YOUR CODE HERE ***/
        return 0;
    }
    
}
