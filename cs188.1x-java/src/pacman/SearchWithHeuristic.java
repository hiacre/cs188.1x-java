package pacman;

import java.util.List;

public interface SearchWithHeuristic {
    
    public List<Direction> getActions(final SearchProblem problem, final Heuristic heuristic);
}
