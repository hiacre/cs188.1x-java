/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

/**
 *
 * @author archie
 */
public interface GameStateSuccessor1 {
    
    public Direction getAction();
    public GameState1 getState();
    
}
