/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author archie
 */
public enum Direction {
    
    North(0,1),
    South(0,-1),
    East(1,0),
    West(-1,0),
    Stop(0,0),
    ;
    
    
    
    private final int x;
    private final int y;;
    private final static Map<Direction, Direction> mapLeft;
    private final static Map<Direction, Direction> mapRight;
    private final static Map<Direction, Direction> mapReverse;
    static {
        Map<Direction, Direction> mapLeftTemp = new HashMap<>();
        mapLeftTemp.put(Direction.North, Direction.West);
        mapLeftTemp.put(Direction.South, Direction.East);
        mapLeftTemp.put(Direction.East, Direction.North);
        mapLeftTemp.put(Direction.West, Direction.South);
        mapLeftTemp.put(Direction.Stop, Direction.Stop);
        mapLeft = Collections.unmodifiableMap(mapLeftTemp);
        
        Map<Direction, Direction> mapRightTemp = new HashMap<>();
        mapRightTemp.put(Direction.North, Direction.East);
        mapRightTemp.put(Direction.South, Direction.West);
        mapRightTemp.put(Direction.East, Direction.South);
        mapRightTemp.put(Direction.West, Direction.North);
        mapRightTemp.put(Direction.Stop, Direction.Stop);
        mapRight = Collections.unmodifiableMap(mapRightTemp);
        
        Map<Direction, Direction> mapReverseTemp = new HashMap<>();
        mapReverseTemp.put(Direction.North, Direction.South);
        mapReverseTemp.put(Direction.South, Direction.North);
        mapReverseTemp.put(Direction.East, Direction.West);
        mapReverseTemp.put(Direction.West, Direction.East);
        mapReverseTemp.put(Direction.Stop, Direction.Stop);
        mapReverse = Collections.unmodifiableMap(mapReverseTemp);
    }
    
    
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
    
    public Direction turnLeft() {
        return mapLeft.get(this);
    }
    public Direction turnRight() {
        return mapRight.get(this);
    }
    public Direction reverse() {
        return mapReverse.get(this);
    }
}
