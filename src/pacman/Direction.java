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
    
    public DirectionVector toVector() {
        return toVector(1);
    }
    
    public DirectionVector toVector(final double speed) {
        return DirectionVector.newInstance(this.x * speed, this.y * speed);
    }

    public Direction getReverse() {
        switch(this) {
            case North: return Direction.South;
            case South: return Direction.North;
            case East: return Direction.West;
            case West: return Direction.East;
            case Stop: return Direction.Stop;
            default: throw new RuntimeException("Unhandled direction");
        }
    }
}
