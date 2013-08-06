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
    
    private double x;
    private double y;
    
    private DirectionVector(final double x, final double y) {
        this.x = x;
        this.y = y;
    }
    
    public static DirectionVector newInstance(final double x, final double y) {
        return new DirectionVector(x, y);
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
}
