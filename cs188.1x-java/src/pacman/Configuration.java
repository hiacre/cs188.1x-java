package pacman;

import common.Position;

/**
 * A Configuration holds the (x,y) coordinate of a character, along with its
    traveling direction.

    The convention for positions, like a graph, is that (0,0) is the lower left corner, x increases
    horizontally and y increases vertically.  Therefore, north is the direction of increasing y, or (0,1).
 */
public interface Configuration {
    
    public Position getPosition();
    public Direction getDirection();
    public boolean isInteger();
    public Configuration generateSuccessor(final DirectionVector vector);
    
}
