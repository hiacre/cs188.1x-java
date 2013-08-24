package pacman;

import util.Position;

/**
 *
 * @author archie
 */
public class AgentTypeAndPosition implements Comparable<AgentTypeAndPosition> {

    private final boolean isPacman;
    private final Position position;
    private final int value;
    
    public AgentTypeAndPosition(final Position position, final int value) {
        this.value = value;
        this.isPacman = (value == 0);
        this.position = position;
    }
    
    public boolean isPacman() {
        return isPacman;
    }
    
    public Position getPosition() {
        return position;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int compareTo(AgentTypeAndPosition o) {
        return value - o.getValue();
    }

}
