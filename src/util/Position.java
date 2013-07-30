/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author archie
 */
public interface Position {
    
    public int getX();
    public int getY();
    public int manhattanDistance(Position point);
    
}
