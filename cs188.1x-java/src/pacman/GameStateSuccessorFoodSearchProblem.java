package pacman;

class GameStateSuccessorFoodSearchProblem implements GameStateSuccessor {
    
    private final GameStateFoodSearchProblem gameState;
    private final Direction direction;
    private final int cost;

    public GameStateSuccessorFoodSearchProblem(
            GameStateFoodSearchProblem gameState,
            Direction direction,
            int cost) {
        this.gameState = gameState;
        this.direction = direction;
        this.cost = cost;
    }
    
}
