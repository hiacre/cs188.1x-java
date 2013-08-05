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
public class AgentStateSimple implements AgentState {
    private final ConfigurationStandard configuration;
    private final boolean isPacman;

    public AgentStateSimple(ConfigurationStandard configuration, boolean isPacman) {
        this.configuration = configuration;
        this.isPacman = isPacman;
    }

    @Override
    public AgentState copy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Position getPosition() {
        return configuration.getPosition();
    }

    @Override
    public Direction getDirection() {
        return configuration.getDirection();
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }
    
}
