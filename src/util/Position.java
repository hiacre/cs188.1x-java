/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 * The convention for positions, like a graph, is that (0,0) is the lower left
 * corner, x increases horizontally and y increases vertically.  Therefore,
 * north is the direction of increasing y, or (0,1).
 * @author archie
 */
public interface Position {
    
    public int getX();
    public int getY();
    public int manhattanDistance(Position point);
    
}
