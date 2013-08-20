package pacman;

/**
 * A SearchAgent for FoodSearchProblem using A* and your foodHeuristic
 * @author archie
 */
public class AStarCornersAgent extends SearchAgent {

    public AStarCornersAgent() {
        super(
            new AStarSearch(),
            new CornersProblemFactory(),
            new CornersHeuristic());
    }

}
