/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
}
