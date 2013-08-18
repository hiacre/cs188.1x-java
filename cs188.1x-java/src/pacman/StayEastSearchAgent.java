package pacman;

import util.Position;

/**
 * An agent for position search with a cost function that penalizes being in
    positions on the West side of the board.

    The cost function for stepping into a position (x,y) is 1/2^x.
 * @author archie
 */
public class StayEastSearchAgent implements SearchAgent {

    public StayEastSearchAgent() {
        this.searchFunction = search.uniformCostSearch;
        final CostFunction costFn = new CostFunction() {

            @Override
            public int eval(Position pos) {
                return Math.pow(0.5, pos.getX());
            }
        };
        this.searchType = lambda state: PositionSearchProblem(state, costFn);
    }
}
