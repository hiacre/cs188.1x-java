package util;

/**
 * The convention for positions, like a graph, is that (0,0) is the lower left
 * corner, x increases horizontally and y increases vertically.  Therefore,
 * north is the direction of increasing y, or (0,1).
 * @author archie
 */
public interface Position {
    
    public double getX();
    public double getY();
    public double manhattanDistance(Position point);
    public int getRoundedX();
    public int getRoundedY();
    public int getFloorX();
    public int getFloorY();
    
}
