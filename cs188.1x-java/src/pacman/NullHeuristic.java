package pacman;

/**
 *     A heuristic function estimates the cost from the current state to the nearest
    goal in the provided SearchProblem.  This heuristic is trivial.
 */
public class NullHeuristic implements Heuristic {

    public NullHeuristic() {
    }

    @Override
    public int calculate(final GameState1 state, final SearchProblem problem) {
        return 0;
    }

    
}
