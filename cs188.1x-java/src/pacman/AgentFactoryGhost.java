package pacman;

/**
 *
 * @author archie
 */
public interface AgentFactoryGhost extends AgentFactory {
    
    public GhostAgent make(final int index);
    
}
