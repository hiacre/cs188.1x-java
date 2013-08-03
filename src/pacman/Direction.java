/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

/**
 *
 * @author archie
 */
public enum Direction {
    
    North(0,1),
    South(0,-1),
    East(1,0),
    West(-1,0),
    Stop(0,0);
    
    private final int x;
    private final int y;
    
    private Direction(final int x, final int y) {
        this.x = x;
        this.y = y;
    }
    
    public DirectionVector toVector(Integer speed) {
        if(speed == null) {
            speed = 1;
        }
        
        return DirectionVector.newInstance(this.x * speed, this.y * speed);
    }
}
