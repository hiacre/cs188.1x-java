package pacman;

import java.util.List;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Search the node that has the lowest combined cost and heuristic first.
 * @author archie
 */
public class SearchAStar implements SearchWithHeuristic {

    @Override
    public List<Direction> getActions(SearchProblem problem, Heuristic heuristic) {
        if(heuristic == null) {
            heuristic = new NullHeuristic();
        }
        
        throw new NotImplementedException();
    }
    
}
