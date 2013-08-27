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
    public void setConfiguration(Configuration generateSuccessor);
    public boolean isPacman();
    
}
