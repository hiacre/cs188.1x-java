package pacman;

/**
 * A SearchAgent for FoodSearchProblem using A* and your foodHeuristic
 */
public class AStarCornersAgent extends SearchAgent {

    public AStarCornersAgent() {
        super(
            new AStarSearch(),
            new CornersProblemFactory(),
            new CornersHeuristic());
    }

}
