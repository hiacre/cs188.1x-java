/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import util.Position;

/**
 * An agent for position search with a cost function that penalizes being in
    positions on the East side of the board.

    The cost function for stepping into a position (x,y) is 2^x.
 * @author archie
 */
public class StayWestSearchAgent extends SearchAgent {

    public StayWestSearchAgent() {
        this.searchFunction = search.uniformCostSearch;
        final CostFunction costFn = new CostFunction() {

            @Override
            public int eval(Position pos) {
                return Math.pow(2, pos.getX());
            }
        };
        
        // searchType is a function that takes a state, and returns a problem
        this.searchType = lambda state: PositionSearchProblem(state, costFn);
    }

}
