package pacman;

import common.Position;

public interface CostFunction {

    public int eval(Position nextState);
    
}
