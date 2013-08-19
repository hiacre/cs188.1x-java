package pacman;

/**
 *
 * @author archie
 */
class PositionSearchProblemFactory implements ProblemFactory {
    
    private final CostFunction costFunction;

    public PositionSearchProblemFactory() {
        this.costFunction = new CostFunctionAlwaysOne();
    }
    
    public PositionSearchProblemFactory(final CostFunction costFunction) {
        this.costFunction = costFunction;
    }

    @Override
    public PositionSearchProblem makeProblem(GameState1 state) {
        return new PositionSearchProblem(
                state, costFunction, null, new GameStatePositionSearchProblem(state.getPacmanPosition()), null, null);
    }
    
}
