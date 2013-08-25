package pacman;

import java.util.List;

/**
 *
 * @author archie
 */
public interface SearchWithHeuristic {
    
    public List<Direction> getActions(final SearchProblem problem, final Heuristic heuristic);
}
