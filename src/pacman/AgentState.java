/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import util.Position;

/**
 *
 * @author archie
 */
public interface AgentState {
    
    public AgentState copy();
    public Position getPosition();
    public Direction getDirection();
    public Configuration getConfiguration();
}
