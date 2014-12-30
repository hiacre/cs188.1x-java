package pacman;

public interface AgentFactoryGhost extends AgentFactory {
    
    public GhostAgent make(final int index);
    
}
