/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

/**
 *
 * @author archie
 */
class GameStateSuccessorFoodSearchProblemStandard {
    
    private final GameStateFoodSearchProblemStandard gameState;
    private final Direction direction;
    private final int cost;

    public GameStateSuccessorFoodSearchProblemStandard(
            GameStateFoodSearchProblemStandard gameState,
            Direction direction,
            int cost) {
        this.gameState = gameState;
        this.direction = direction;
        this.cost = cost;
    }
    
}
