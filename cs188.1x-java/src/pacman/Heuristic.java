package pacman;

/**
 *
 * @author archie
 */
public interface Heuristic {
    
    public int calculate(GameState1 state, SearchProblem problem);
}
