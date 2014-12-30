package pacman;

import common.Position;

public class AgentStateSimple implements AgentState {
    private Configuration configuration;
    private final boolean isPacman;
    private final Configuration startConfiguration;

    public AgentStateSimple(Configuration startConfiguration, boolean isPacman) {
        this.startConfiguration = startConfiguration;
        this.configuration = startConfiguration;
        this.isPacman = isPacman;
    }

    @Override
    public AgentState copy() {
        throw new UnsupportedOperationException("Not supported yet.");
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
    
    @Override
    public void setConfiguration(final Configuration configuration) {
        this.configuration = configuration;
    }
    
    public Configuration getStart() {
        return startConfiguration;
    }
    
    @Override
    public boolean isPacman() {
        return isPacman;
    }
}
