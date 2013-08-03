/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

/**
 *
 * @author archie
 */
public class DirectionVector {
    
    private int x;
    private int y;
    
    private DirectionVector(final int x, final int y) {
        this.x = x;
        this.y = y;
    }
    
    public static DirectionVector newInstance(final int x, final int y) {
        return new DirectionVector(x, y);
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
}
