package pacman;

public interface Heuristic<P extends SearchProblem> {
    
    public int calculate(GameState1 state, P problem);
}
