package pacman;

import common.Position;

/**
 * An agent for position search with a cost function that penalizes being in
    positions on the East side of the board.

    The cost function for stepping into a position (x,y) is 2^x.
 */
public class StayWestSearchAgent extends SearchAgent {

    public StayWestSearchAgent() {
        super(
            new UniformCostSearch(),
            new PositionSearchProblemFactory(
                new CostFunction() {
                    @Override
                    public int eval(Position pos) {
                        return (int) Math.pow(2, pos.getX());
                    }
                }),
            null
            );
    }
}
