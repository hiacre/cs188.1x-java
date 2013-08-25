package pacman;

/**
 *
 * @author archie
 */
public class GhostState extends AgentStateSimple {
    
    public GhostState(final Configuration configuration) {
        super(configuration, false);
    }
    private int scaredTimer;
    
    public int getScaredTimer() {
        return scaredTimer;
    }

    void setScaredTimer(int scaredTimer) {
        this.scaredTimer = scaredTimer;
    }
}
