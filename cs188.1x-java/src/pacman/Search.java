package pacman;

import java.util.List;

public interface Search {
    
    public List<Direction> getActions(final SearchProblem problem);
}
