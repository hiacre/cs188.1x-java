/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

/**
 *
 * @author archie
 */
class GameStateSuccessorCornersProblemStandard implements GameStateSuccessorCornersProblem {
    
    private final GameStateCornersProblemStandard gameState;
    private final Direction action;
    private final int costOfActions;

    public GameStateSuccessorCornersProblemStandard(
            final GameStateCornersProblemStandard gameState,
            final Direction action,
            final int costOfActions) {
        this.gameState = gameState;
        this.action = action;
        this.costOfActions = costOfActions;
    }
    
}
