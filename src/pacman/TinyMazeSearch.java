package pacman;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author archie
 */
public class TinyMazeSearch implements Search {

    @Override
    public List<Direction> getActions(SearchProblem problem) {
        /**
         * Returns a sequence of moves that solves tinyMaze.  For any other
         * maze, the sequence of moves will be incorrect, so only use this for tinyMaze
         */
        final Direction s = Direction.South;
        final Direction w = Direction.West;
        
        return Arrays.asList(s,s,w,s,w,w,s,w);
    }
    
}
