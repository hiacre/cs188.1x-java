/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

/**
 *
 * @author archie
 */
class GameStateSuccessor1Standard implements GameStateSuccessor1 {
    private final GameState1 gameState;
    private final Direction action;

    public GameStateSuccessor1Standard(final GameState1 gameState, final Direction action) {
        this.gameState = gameState;
        this.action = action;
        
    }
    
}
