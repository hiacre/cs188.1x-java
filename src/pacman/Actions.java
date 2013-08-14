/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import util.Position;
import util.PositionStandard;

/**
 * A collection of static methods for manipulating move actions.
 * @author archie
 */
public class Actions {
    // Directions
    private static Map<Direction, DirectionVector> _directions;
    
    static {
        final Map<Direction, DirectionVector> mapDirectionRelativePos = new HashMap<>();
        mapDirectionRelativePos.put(Direction.North, DirectionVector.newInstance(0,1));
        mapDirectionRelativePos.put(Direction.South, DirectionVector.newInstance(0,-1));
        mapDirectionRelativePos.put(Direction.East, DirectionVector.newInstance(1,0));
        mapDirectionRelativePos.put(Direction.West, DirectionVector.newInstance(-1,0));
        mapDirectionRelativePos.put(Direction.Stop, DirectionVector.newInstance(0,0));
        _directions = Collections.unmodifiableMap(mapDirectionRelativePos);
    }

//    _directionsAsList = _directions.items();
//
    private static double TOLERANCE = .001;
    

    public static List<Direction> getPossibleActions(final Configuration config, final Grid walls) {
        final List<Direction> possible = new ArrayList<>();
        final Position pos = config.getPosition();
       
        final int x = pos.getX();
        final int y = pos.getY();
       
        // in the original Python, some rounding occurs here,
        // so it's possible that Position should be able to store floating point values.
        // or this might have been put in to deal with a Python deficiency.
        final int x_int = x;
        final int y_int = y;

        //# In between grid points, all agents must continue straight
        if(Math.abs(x - x_int) + Math.abs(y - y_int) > TOLERANCE) {
            return Arrays.asList(config.getDirection());
        }
        for(Entry<Direction, DirectionVector> dirVec : _directions.entrySet()) {
            final Direction dir = dirVec.getKey();
            final DirectionVector vec = dirVec.getValue();
            final int next_y = y_int + vec.getY();
            final int next_x = x_int + vec.getX();
            if(!walls.get(next_x, next_y)) {
                possible.add(dir);
            }
        }

        return possible;
    }

    public static List<Position> getLegalNeighbors(final Position position, final Grid walls) {
        final int x_int = position.getX();
        final int y_int = position.getY();
        final List neighbors = new ArrayList();
        for(Entry<Direction, DirectionVector> dirVec : _directions.entrySet()) {
            final Direction dir = dirVec.getKey();
            final DirectionVector vec = dirVec.getValue();
            final int dx = vec.getX();
            final int dy = vec.getY();
            final int next_x = x_int + dx;
            if(next_x < 0 || next_x == walls.getWidth()) {
                continue;
            }
            final int next_y = y_int + dy;
            if(next_y < 0 || next_y == walls.getHeight()) {
                continue;
            }
            if(!walls.get(next_x, next_y)) {
                neighbors.add(PositionStandard.newInstance(next_x, next_y));
            }
        }
        return neighbors;
    }

    public static Position getSuccessor(final Position position, final Direction action) {
        final DirectionVector posVec = action.toVector(null);
        final int dx = posVec.getX();
        final int dy = posVec.getY();
        final int x = position.getX();
        final int y = position.getY();
        return PositionStandard.newInstance(x + dx, y + dy);
    }
}
