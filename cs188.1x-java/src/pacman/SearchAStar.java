package pacman;

import java.util.List;

/**
 * Search the node that has the lowest combined cost and heuristic first.
 */
public class SearchAStar implements SearchWithHeuristic {

    @Override
    public List<Direction> getActions(SearchProblem problem, Heuristic heuristic) {
        if(heuristic == null) {
            heuristic = new NullHeuristic();
        }
        
        throw new UnsupportedOperationException();
    }
    
}
