/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.Position;
import util.PositionStandard;

/**
 * A collection of static methods for manipulating move actions.
 * @author archie
 */
public class Actions {
    // Directions
    private static Map<Direction, Position> _directions;
    
    static {
        final Map<Direction, Position> mapDirectionRelativePos = new HashMap<>();
        mapDirectionRelativePos.put(Direction.North, PositionStandard.newInstance(0,1));
        mapDirectionRelativePos.put(Direction.South, PositionStandard.newInstance(0,-1));
        mapDirectionRelativePos.put(Direction.East, PositionStandard.newInstance(1,0));
        mapDirectionRelativePos.put(Direction.West, PositionStandard.newInstance(-1,0));
        mapDirectionRelativePos.put(Direction.Stop, PositionStandard.newInstance(0,0));
        _directions = Collections.unmodifiableMap(mapDirectionRelativePos);
    }

//    _directionsAsList = _directions.items();
//
//    TOLERANCE = .001;
//
//    def reverseDirection(action):
//        if action == Directions.NORTH:
//            return Directions.SOUTH
//        if action == Directions.SOUTH:
//            return Directions.NORTH
//        if action == Directions.EAST:
//            return Directions.WEST
//        if action == Directions.WEST:
//            return Directions.EAST
//        return action
//    reverseDirection = staticmethod(reverseDirection)
//
//    def vectorToDirection(vector):
//        dx, dy = vector
//        if dy > 0:
//            return Directions.NORTH
//        if dy < 0:
//            return Directions.SOUTH
//        if dx < 0:
//            return Directions.WEST
//        if dx > 0:
//            return Directions.EAST
//        return Directions.STOP
//    vectorToDirection = staticmethod(vectorToDirection)

    

    public static Object getPossibleActions(final Configuration config, final Object walls) {
        final List<Object> possible = new ArrayList<>();
        final Position pos = config.getPosition();
        
        // in the original Python, some rounding occurs here,
        // so it's possible that Position should be able to store floating point values.
        final int x_int = pos.getX();
        final int y_int = pos.getY();

        //# In between grid points, all agents must continue straight
        if (abs(x - x_int) + abs(y - y_int)  > Actions.TOLERANCE):
            return [config.getDirection()]

        for dir, vec in Actions._directionsAsList:
            dx, dy = vec
            next_y = y_int + dy
            next_x = x_int + dx
            if not walls[next_x][next_y]: possible.append(dir)

        return possible;
    }

    getPossibleActions = staticmethod(getPossibleActions)

    def getLegalNeighbors(position, walls):
        x,y = position
        x_int, y_int = int(x + 0.5), int(y + 0.5)
        neighbors = []
        for dir, vec in Actions._directionsAsList:
            dx, dy = vec
            next_x = x_int + dx
            if next_x < 0 or next_x == walls.width: continue
            next_y = y_int + dy
            if next_y < 0 or next_y == walls.height: continue
            if not walls[next_x][next_y]: neighbors.append((next_x, next_y))
        return neighbors
    getLegalNeighbors = staticmethod(getLegalNeighbors)

    def getSuccessor(position, action):
        dx, dy = Actions.directionToVector(action)
        x, y = position
        return (x + dx, y + dy)
    getSuccessor = staticmethod(getSuccessor)    
}
