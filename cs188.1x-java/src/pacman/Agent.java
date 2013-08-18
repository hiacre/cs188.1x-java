/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

/**
 *
 * @author archie
 */
public interface Agent {
    
    public Direction getAction(GameState1 state);
    
    /** Inspects the starting state **/
    public void registerInitialState(GameState1 state);
    
}
