package pacman;

public class CornersProblemFactory implements ProblemFactory {

    public CornersProblemFactory() {
    }

    @Override
    public SearchProblem makeProblem(GameState1 state) {
        return new CornersProblem(state);
    }
    
}
