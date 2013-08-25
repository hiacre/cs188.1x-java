package pacman;

/**
 *
 * @author archie
 */
class GameStateSuccessorCornersProblemStandard implements GameStateSuccessorCornersProblem {
    
    private final GameStateCornersProblem gameState;
    private final Direction action;
    private final int costOfActions;

    public GameStateSuccessorCornersProblemStandard(
            final GameStateCornersProblem gameState,
            final Direction action,
            final int costOfActions) {
        this.gameState = gameState;
        this.action = action;
        this.costOfActions = costOfActions;
    }
    
}
