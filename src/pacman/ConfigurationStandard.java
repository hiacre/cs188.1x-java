/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.util.Objects;
import util.Position;
import util.PositionStandard;

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
        final double x = this.getPosition().getX();
        final double y = this.getPosition().getY();
        return x == Math.floor(x) && y == Math.floor(y);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.position);
        hash = 67 * hash + Objects.hashCode(this.direction);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConfigurationStandard other = (ConfigurationStandard) obj;
        if (!Objects.equals(this.position, other.position)) {
            return false;
        }
        if (this.direction != other.direction) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "(x,y)="+this.position+", "+this.direction;
    }
    
    
    @Override
    /**
     * Generates a new configuration reached by translating the current
        configuration by the action vector.  This is a low-level call and does
        not attempt to respect the legality of the movement.

        Actions are movement vectors.
     */
    public Configuration generateSuccessor(final DirectionVector vector) {
        final int x = this.position.getX();
        final int y = this.position.getY();
        final double dx = vector.getX();
        final double dy = vector.getY();
        Direction direction = vector.toDirection();
        if(Direction.Stop.equals(direction)) {
            direction = this.direction;  // There is no stop direction
        }
        return new ConfigurationStandard(
                PositionStandard.newInstance(x + dx, y+dy),
                direction);
    }
    
}
