package pacman;

import java.util.Arrays;
import java.util.List;

public class SearchTinyMaze implements Search {
    
    @Override
    public List<Direction> getActions(final SearchProblem problem) {
        final Direction s = Direction.South;
        final Direction w = Direction.West;
        return Arrays.asList(s,s,w,s,w,w,s,w);
    }
}
