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
public class ConfigurationStandard implements Configuration {
    
    private final Position position;
    private final Direction direction;

    public ConfigurationStandard(final Position position, final Direction direction) {
        this.position = position;
        this.direction = direction;
    }
    
    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public boolean isInteger() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Configuration generateSuccessor(DirectionVector vector) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
