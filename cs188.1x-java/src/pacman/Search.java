package pacman;

import java.util.List;

/**
 *
 * @author archie
 */
public interface Search {
    
    public List<Direction> getActions(final SearchProblem problem);
}
