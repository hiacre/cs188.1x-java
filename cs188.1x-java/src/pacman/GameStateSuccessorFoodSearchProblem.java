package pacman;

/**
 *
 * @author archie
 */
class GameStateSuccessorFoodSearchProblem implements GameStateSuccessor {
    
    private final GameStateFoodSearchProblemStandard gameState;
    private final Direction direction;
    private final int cost;

    public GameStateSuccessorFoodSearchProblem(
            GameStateFoodSearchProblemStandard gameState,
            Direction direction,
            int cost) {
        this.gameState = gameState;
        this.direction = direction;
        this.cost = cost;
    }
    
}
