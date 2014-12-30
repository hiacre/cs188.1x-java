package pacman;

import common.Position;

public interface AgentState {
    
    public AgentState copy();
    public Position getPosition();
    public Direction getDirection();
    public Configuration getConfiguration();
    public void setConfiguration(Configuration generateSuccessor);
    public boolean isPacman();
    
}
