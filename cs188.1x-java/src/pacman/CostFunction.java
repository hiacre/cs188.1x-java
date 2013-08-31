package pacman;

import common.Position;

/**
 *
 * @author archie
 */
public interface CostFunction {

    public int eval(Position nextState);
    
}
