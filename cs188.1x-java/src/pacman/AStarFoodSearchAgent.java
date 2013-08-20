package pacman;

/**
 * A SearchAgent for FoodSearchProblem using A* and your foodHeuristic
 * @author archie
 */
public class AStarFoodSearchAgent extends SearchAgent {
    
    public AStarFoodSearchAgent() {
        super(new AStarSearch(), new FoodSearchProblemFactory(), new FoodHeuristic());
    }
    
    
}
