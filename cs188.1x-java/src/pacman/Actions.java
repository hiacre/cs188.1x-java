package pacman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import util.Position;

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
        
        final double x = pos.getX();
        final double y = pos.getY();
        final int x_int = pos.getRoundedX();
        final int y_int = pos.getRoundedY();

        // In between grid points, all agents must continue straight
        if(Math.abs(x - x_int) + Math.abs(y - y_int) > TOLERANCE) {
            return Arrays.asList(config.getDirection());
        }
        for(Entry<Direction, DirectionVector> dirVec : _directions.entrySet()) {
            final Direction dir = dirVec.getKey();
            final DirectionVector vec = dirVec.getValue();
            final int next_y = y_int + (int)vec.getY();
            final int next_x = x_int + (int)vec.getX();
            if(!walls.get(next_x, next_y)) {
                possible.add(dir);
            }
        }

        return possible;
    }

    public static List<Position> getLegalNeighbors(final Position position, final Grid walls) {
        final int x_int = position.getRoundedX();
        final int y_int = position.getRoundedY();
        final List neighbors = new ArrayList();
        for(Entry<Direction, DirectionVector> dirVec : _directions.entrySet()) {
            final Direction dir = dirVec.getKey();
            final DirectionVector vec = dirVec.getValue();
            final int dx = (int)vec.getX();
            final int dy = (int)vec.getY();
            final int next_x = x_int + dx;
            if(next_x < 0 || next_x == walls.getWidth()) {
                continue;
            }
            final int next_y = y_int + dy;
            if(next_y < 0 || next_y == walls.getHeight()) {
                continue;
            }
            if(!walls.get(next_x, next_y)) {
                neighbors.add(Position.newInstance(next_x, next_y));
            }
        }
        return neighbors;
    }

    public static Position getSuccessor(final Position position, final Direction action) {
        final DirectionVector posVec = action.toVector();
        final double dx = posVec.getX();
        final double dy = posVec.getY();
        final double x = position.getX();
        final double y = position.getY();
        return Position.newInstance(x + dx, y + dy);
    }
}
