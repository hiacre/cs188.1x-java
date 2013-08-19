package pacman;

import util.Position;

/**
 * An agent for position search with a cost function that penalizes being in
    positions on the West side of the board.

    The cost function for stepping into a position (x,y) is 1/2^x.
 * @author archie
 */
public class StayEastSearchAgent extends SearchAgent {

    public StayEastSearchAgent() {
        super(
                new UniformCostSearch(),
                new PositionSearchProblemFactory(
                   new CostFunction() {
                        @Override
                        public int eval(Position pos) {
                            return (int)Math.pow(0.5, pos.getX());
                        }
                    }
                ),
                null);
    }
}
