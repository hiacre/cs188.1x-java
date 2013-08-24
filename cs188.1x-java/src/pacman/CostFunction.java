package pacman;

import util.Position;

/**
 *
 * @author archie
 */
public interface CostFunction {

    public int eval(Position nextState);
    
}
