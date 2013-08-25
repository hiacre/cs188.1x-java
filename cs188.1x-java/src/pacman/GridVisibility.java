package pacman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import util.Position;

/**
 *
 * @author archie
 */
public class GridVisibility {
    // TODO maybe this class can implement Grid in some way?
    private final List<List<Map<Direction,Set<Position>>>> data;
    
    private GridVisibility(final int width, final int height) {
        data = new ArrayList(width);
        for(int x=0; x<width; x++) {
            final List row = new ArrayList(height);
            for(int y=0; y<height; y++) {
                final Map<Direction, Set<Object>> mapDirectionSet = new HashMap<>();
                mapDirectionSet.put(Direction.North, new HashSet());
                mapDirectionSet.put(Direction.South, new HashSet());
                mapDirectionSet.put(Direction.East, new HashSet());
                mapDirectionSet.put(Direction.West, new HashSet());
                mapDirectionSet.put(Direction.Stop, new HashSet());
                row.set(y, mapDirectionSet);
            }
            data.set(x, row);
        }
    }
    
    public static GridVisibility newInstance(final int width, final int height) {
        return new GridVisibility(width, height);
    }

    public Set<Position> get(int row, int col, final Direction direction) {
        return data.get(row).get(col).get(direction);
    }
}
